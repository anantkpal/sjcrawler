package in.anantkpal.sjcrawler.crawler;

/**
  @author Anant Pal (anantkpal@yahoo.co.in)
*/

import in.anantkpal.sjcrawler.crawler.CrawlerResult;

import java.net.URL;

/**
  Interface all the Crawler should implement
*/
public interface ICrawler{

  /**
    This function will go to the Page URL and update the <b>title</b> of the page as well add child pages
    @param url This is the url that needs to be crawled
    @return the CrawlerResult details of the URL that is crawled
  */
  public CrawlerResult crawl(URL url);

}
