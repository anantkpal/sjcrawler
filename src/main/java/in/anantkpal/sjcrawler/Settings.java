package in.anantkpal.sjcrawler;

/**
  @author Anant Pal(anantkpal@yahoo.co.in)
*/

import in.anantkpal.sjcrawler.validator.IValidator;
import in.anantkpal.sjcrawler.page.Page;

import java.net.URL;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  This is the Settings for the SJCrawler instance
*/
public class Settings{



  //  This is the crawler logger
  Logger logger = LoggerFactory.getLogger(Settings.class);

  /**
      This is the number of threads on the JVM the  crawler should make use of.
      min is 1(main Process)+1(Crawling Process)
  */
  private int noOfThreads;


  /**
    Can navigate URL validators
  */
  List<IValidator> canNavigateValidators = new ArrayList<IValidator>();

  /**
    This is the crawler type
  */
  private String crawlerType;

  /**
    Setter for the noOfThreads
    @param noOfThreads This is the noOfThreads settings
  */
  public void setNoOfThreads(int noOfThreads){
    this.noOfThreads = noOfThreads;
  }

  /**
    This is getter for number of threads
    @return No of Threads the crawler process is using
  */
  public int getNoOfThreads(){
    return this.noOfThreads;
  }

  /**
    Setter for the crawlerType
    @param crawlerType this is the crawler type that needs to be used
  */
  public void setCrawlerType(String crawlerType){
    this.crawlerType = crawlerType;
  }

  /**
    Getter for the crawlerType
    @return crawlerType which has been set
  */
  public String getCrawlerType(){
    return this.crawlerType;
  }

  /**
    Adding new can navigate validators
    @param validator This is the validator that will added to the repository
  */
  public void addCanNavigateValidator(IValidator validator){
    this.canNavigateValidators.add(validator);
  }

  /**
    This will validate whether the URL can be navigated or not
    @param childPage This is the Page which needs to be validated for navigation.
  */
  public boolean canNavigate(Page childPage){
    for (IValidator  validator:  canNavigateValidators) {
      if(!validator.validate(childPage)){
        return false;
      }
    }
    return true;
  }


}
