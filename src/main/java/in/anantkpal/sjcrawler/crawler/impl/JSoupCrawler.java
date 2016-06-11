package in.anantkpal.sjcrawler.crawler.impl;


/**
  @author Anant Pal(anantkpal@yahoo.co.in)
*/

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import in.anantkpal.sjcrawler.crawler.ICrawler;
import in.anantkpal.sjcrawler.crawler.CrawlerResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
  Jsoup implementation of the crawler
*/
public class JSoupCrawler implements ICrawler{


  /**
    This is the crawler logger
  */
  Logger logger = LoggerFactory.getLogger(JSoupCrawler.class);


  /**
    Crawlere implementation, This must function that needs to implementation due to Interface ICrawler
    @param url This is the URL that is crawled.
  */
  public CrawlerResult crawl(URL url){
    //Creating the crawler result
    CrawlerResult rslt = new CrawlerResult(url);
    logger.info(url +" started crawling....");
    try{
      //Get the Document from the URL
      Document doc = Jsoup.connect(url.toString()).get();

      rslt.setTitle(doc.title());

      //links on the page
      Elements links = doc.select("a");

      //Links on the page
      for(Element link : links){
        rslt.addLinksOnPage(new URL(link.attr("abs:href")));
      }
    }catch(Exception e){
      //TODO
    }
    logger.info(url +" finished crawled.");
    return rslt;
  }


}
