package in.anantkpal.sjcrawler.crawler.impl;

/**
  @author Anant Pal(anantkpal@yahoo.co.in)
*/

import in.anantkpal.sjcrawler.crawler.CrawlerResult;
import in.anantkpal.sjcrawler.crawler.impl.JSoupCrawler;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import java.net.URL;

/**
  This is the test class for the class {@link JSoupCrawler}
*/

public class JSoupCrawlerTest{

  CrawlerResult rslt;


  /**
    Preparing for the test
  */
  @Before
  public void setUp(){

    JSoupCrawler crawler = new JSoupCrawler();
    try{
      rslt = crawler.crawl(new URL("http://www.anantkpal.in"));
    }catch(Exception e){
        e.printStackTrace();
    }
  }

  /**
    Validating the titleof the page
    <u>Title of the page should be <i>Anant Pal (@anantkpal)</i></u>
  */
  @Test
  public void validateTitle(){
    assertEquals("Anant Pal (@anantkpal)",rslt.getTitle());
  }


  /**
    Validating the amount of the links on the page.
    <u> No of links on the page as of 06-06-2015 is 19</u>
  */
  @Test
  public void validateLinks(){
    assertEquals(20,rslt.getLinksOnPage().size());
  }

}
