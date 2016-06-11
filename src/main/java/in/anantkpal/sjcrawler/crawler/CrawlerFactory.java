package in.anantkpal.sjcrawler.crawler;

/**
  @author Anant Pal (anantkpal@yahoo.co.in)
*/

import in.anantkpal.sjcrawler.crawler.ICrawler;
import in.anantkpal.sjcrawler.crawler.impl.JSoupCrawler;

/**
  This is the factory to create the crawler instance
*/
public class CrawlerFactory{


  /**
    Getting instance of the Crawler
    @param crawlertype This is the crawler type
    @return ICrawler instance
  */
  public ICrawler getCrawlerInstance(String crawlertype){
    ICrawler crawler;
    switch(crawlertype.toLowerCase()) {
         case "jsoup":
              crawler = (new JSoupCrawler());
              break;
        default:
            crawler = (new JSoupCrawler());
            break;
        }
    return crawler;
  }

}
