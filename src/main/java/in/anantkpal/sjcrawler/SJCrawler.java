package in.anantkpal.sjcrawler;

/**
  @author Anant Pal(anantkpal@yahoo.co.in)
*/
import in.anantkpal.sjcrawler.Settings;
import in.anantkpal.sjcrawler.page.PageProcessor;
import in.anantkpal.sjcrawler.page.Page;
import in.anantkpal.sjcrawler.crawler.CrawlerResult;
import in.anantkpal.sjcrawler.crawler.CrawlerFactory;


import java.net.URL;
import java.net.MalformedURLException;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
  This is the main Thread that keeps track of all the crawling activity.
*/
public class SJCrawler{

    //logger
    Logger logger = LoggerFactory.getLogger(SJCrawler.class);


  /**
    ExecutorService instance that will manage the child page processor instances instance
  */
  private ExecutorService pageProcessorExecutor;

  /**
    This is settings of the SJCrawler
  */
  private Settings settings;

  /**
    This is the root URI from which the SJCrawler shoudl start crawling the site
  */
  private URL rootURL;

  /**
    list of pages  navigated
  */
  private List<Page> pages = new ArrayList<Page>();


  /**
    Temporary storage for the tracking the futures
  */
  List<Future<CrawlerResult>> tempFutureResults = new ArrayList<Future<CrawlerResult>>();


  /**
    Crawler factory that will be used to create the crawler instance
  */
  private CrawlerFactory crawlerfactory;


  /**
    Constructs the SJCrawler
  */
  public SJCrawler(CrawlerFactory crawlerfactory){
    this.crawlerfactory = crawlerfactory;
  }

  /**
    Setter for the crawlerfactory
    @param crawlerfactory This is the factory that will be
  */
  public void setCrawlerfactory(CrawlerFactory crawlerfactory){
    this.crawlerfactory = crawlerfactory;
  }

  /**
    Getter for the crawlerfactory
    @return crawlerfactory
  */
  public CrawlerFactory getCrawlerfactory(){
    return this.crawlerfactory;
  }

  /**
    This is to initialise the crawler Engine
  */
  public void configure(String url,Settings settings) throws MalformedURLException{
      //initialising the settings & creating the URL
      this.settings = settings;
      this.rootURL = new URL(url);

      //settings initialise
      this.initialiseSettings();

      //Creating the page processor executer service
      pageProcessorExecutor = Executors.newFixedThreadPool(this.settings.getNoOfThreads());
  }


  /**
    private method to initialise the settings object
  */
  private void initialiseSettings(){
    logger.debug("Initialising settings for the crawler...");

    //initialise settings object as whole
    if(this.settings == null)
        this.settings = new Settings();

    logger.debug("Defualtizing the No. Of Threads....");
    //Defaultize the no. of threads
    if(this.settings.getNoOfThreads() == 0)
      this.settings.setNoOfThreads(1);

    logger.debug("No of threads for thecrawler is set to {}",this.settings.getNoOfThreads());

    logger.debug("Defualtizing the Crawler....");
    //Defaultize crawlertype
    if(this.settings.getCrawlerType() == null)
      this.settings.setCrawlerType("jsoup");
    logger.debug("Crawler type is set to {}",this.settings.getCrawlerType());


    logger.debug("Initialised settings for the crawler.");
  }


  /**
    This will start crawling the site
  */
  public void startCrawl(){
    long timeStarted = System.currentTimeMillis();
    logger.info("Started crawling URL {}",this.rootURL);

    //Create the Page from the root URL
    Page page = new Page(this.rootURL);

    //Add the created the Page to the pages repo.
    this.pages.add(page);


    //Creating the page PageProcessor i.e., JOB for the root URL
    PageProcessor pageProcessr = new PageProcessor(this.crawlerfactory.getCrawlerInstance(this.settings.getCrawlerType()),this.rootURL);
      try{
        //Create Future of the submitted JOB
        Future<CrawlerResult> future = pageProcessorExecutor.submit(pageProcessr);

        //Add future to the tracking list
        tempFutureResults.add(future);

        //Check whether there are futures (Crawler Jobs) which are running
        while(tempFutureResults.size()!=0){

          ListIterator<Future<CrawlerResult>> iter = tempFutureResults.listIterator();

            //Loop through all the Futures(Jobs)
            while (iter.hasNext()) {
              Future<CrawlerResult> puture = iter.next();

              if(puture.isDone()){

                    //Processing the result
                    CrawlerResult rslt = puture.get();

                    //Remove this putture i.e., finished why I should keep track of
                    iter.remove();

                    //if rslt is null i.e,
                    //If there is an exceprion while crawling supress it in crawler
                    // We are interesetd in crawling and getting page links
                    if(rslt == null || rslt.getTitle() == null)
                      continue;


                    //Create a page instance using link that is found from the CrawlerResult
                    Page rslPage = new Page(rslt.getLink());

                    //Get the index of the page location
                    int in = pages.indexOf(rslPage);

                    //Set the page title if obtained from CrawlerResult
                    this.pages.get(in).setTitle(rslt.getTitle());

                    //Process the childrens
                    List<URL> childrens = rslt.getLinksOnPage();

                    for (URL childURL : childrens) {

                      //Loop through the childs and check if there are any links already processed
                      Page childPage = new Page(childURL);
                      if(this.pages.indexOf(childPage) == -1){
                        logger.debug("New child URL {} is found",childURL);

                        //update the depth of the page that is parents depth + 1
                        childPage.setDepth(this.pages.get(in).getDepth()+1);

                        //set the sequence number of the page which is nothing but size of the pages array
                        childPage.setSeq(this.pages.size());

                        //add the child to the pages list
                        this.pages.add(childPage);


                        //add parent to the childpage
                        childPage.addParent(this.pages.get(in));


                        //Add this page to child to current
                        this.pages.get(in).addChildPage(childPage);

                        //check ifthe page URL can be navigated if it is create a crawler JOB and it to the pool
                        if(this.settings.canNavigate(childPage)){

                          //Create PageProcessor job instance add it to the JObs Executor
                          future = pageProcessorExecutor.submit(
                              new PageProcessor(this.crawlerfactory.getCrawlerInstance(this.settings.getCrawlerType()),
                              childPage.getLink()));

                          //Add the obtained future for tracking it.
                          iter.add(future);
                        }


                        }else{
                          //If the child element is already processed
                          int nd = this.pages.indexOf(childPage);

                          //add current page as parent to this child
                          this.pages.get(nd).addParent(this.pages.get(in));

                        }
                    }



              }
            }

        }

      }catch(Exception e){
        logger.error("There was error...");
      }
      this.pageProcessorExecutor.shutdown();
      logger.info("Finished crawling URL {} in {}ms",this.rootURL,(System.currentTimeMillis()-timeStarted));

  }

  /**
    This will stop crawling the site
  */
  public void stopCrawl(){
    //This will shutdown the threads gracefully and making sure no new threads initiated
    pageProcessorExecutor.shutdown();
  }


  /**
    Get the list of pages processed
    @return pages extracted by cralwing
  */
  public List<Page> getPages(){
    return this.pages;
  }


}
