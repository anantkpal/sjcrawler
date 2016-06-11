package in.anantkpal.sjcrawler.page;
/**
  @author Anant Pal(anantkpal@yahoo.co.in)
*/

import in.anantkpal.sjcrawler.page.Page;

import in.anantkpal.sjcrawler.crawler.ICrawler;
import in.anantkpal.sjcrawler.crawler.CrawlerResult;

import java.net.URL;

import java.util.concurrent.Callable;

/**
  This class builds the page object using ICrawler & url
*/
public class PageProcessor implements Callable<CrawlerResult>{


    private ICrawler crawler;
    private URL url;

    /**
      Constructs the PageBuilder object
      @param crawler This is the crawler that should be used to crawl the Page
      @param url This is the url that needs to be crawled
    */
    public PageProcessor(ICrawler crawler,URL url){
      this.url = url;
      this.crawler = crawler;
    }

    /**
      Getter for the crawler
      @return ICrawler
    */
    public ICrawler getCrawler(){
      return this.crawler;
    }


    /**
      This will crawl the page and retrun the CrawlerResult
      @return This is the CrawlerResult instance with data
    */
    @Override
    public CrawlerResult call(){
      return crawler.crawl(this.url);
    }


}
