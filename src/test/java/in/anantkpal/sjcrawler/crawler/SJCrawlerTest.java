package in.anantkpal.sjcrawler.crawler;

/**
  @author Anant Pal (anantkpal@yahoo.co.in)
*/

import in.anantkpal.sjcrawler.Settings;
import in.anantkpal.sjcrawler.SJCrawler;
import in.anantkpal.sjcrawler.crawler.CrawlerFactory;
import in.anantkpal.sjcrawler.validator.CrossDomainValidator;
import in.anantkpal.sjcrawler.validator.DepthValidator;



import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import java.net.URL;


/**
  This is the test class for the SJCrawler
*/
public class SJCrawlerTest{

  //Basic Settings
  Settings settings;
  //Crawler instance
  SJCrawler bootCrawler;

  @Before
  public void setUp(){
    try{

      settings = new Settings();
      CrossDomainValidator validator = new CrossDomainValidator(new URL("http://www.anantkpal.in"));
      settings.addCanNavigateValidator(validator);
      settings.setNoOfThreads(5);

      bootCrawler = new SJCrawler(new CrawlerFactory());
      bootCrawler.configure("http://www.anantkpal.in",settings);


    }catch(Exception e){
      e.printStackTrace();
    }
  }

  @Test
  public void validateTitle(){
    bootCrawler.startCrawl();
    assertEquals("Anant Pal (@anantkpal)",bootCrawler.getPages().get(0).getTitle());
  }


  @Test
  public void totalLinks(){
    bootCrawler.startCrawl();
    assertEquals(16,bootCrawler.getPages().size());
  }

  @Test
  public void forBase(){
    DepthValidator validatr = new DepthValidator(0);
    settings.addCanNavigateValidator(validatr);
    try{
      bootCrawler.configure("http://www.anantkpal.in",settings);
    }catch(Exception e){}


    bootCrawler.startCrawl();

    //validating the
    assertEquals(12,bootCrawler.getPages().size());
  }

  @Test
  public void stopCrawl(){
    bootCrawler.stopCrawl();
    bootCrawler.startCrawl();

    //When crawler is stopped it doesn't allow any crawling
    //IN real time scenario this shiuld done after starting in different thread all together
    assertEquals(1,bootCrawler.getPages().size());
  }

}
