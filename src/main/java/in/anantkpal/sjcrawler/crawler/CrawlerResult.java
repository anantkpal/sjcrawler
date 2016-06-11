package in.anantkpal.sjcrawler.crawler;

/**
  @author Anant Pal(anantkpal@yahoo.co.in)
*/


import java.net.URL;

import java.util.List;
import java.util.ArrayList;


/**
  This is the class which holds the basic raw crawled page result
*/
public class CrawlerResult{

      /**
        Title of the page that is crawled
      */
      private String title;

      /**
        This is the Link that is being crawled
      */
      private URL link;

      /**
        List of links on the crawled page
      */
      private List<URL> linksOnPage = new ArrayList<URL>();

      /**
        This creates the instance of the CrawlerResult object
        @param link This is the link that has been crawled
      */
      public CrawlerResult(URL link){
          this.link = link;
      }


      /**
        getter for the URL link that is crawled
        @return URL link of the page that is crawled
      */
      public URL getLink(){
        return this.link;
      }

      /**
        Setter for the setting the titleof the page thatis being rawled
        @param title of the page that is crawled
      */
      public void setTitle(String title){
        this.title = title;
      }


      /**
        Getter for the page title
      */
      public String getTitle(){
        return this.title;
      }


      /**
        Get all the links of the page that is crawled
        @return List of the URL links
      */
      public List<URL> getLinksOnPage(){
        return this.linksOnPage;
      }

      /**
        Add the a new link to the links  on page
        @param link Link on the page that is extracted
      */
      public void addLinksOnPage(URL link){
          this.linksOnPage.add(link);
      }

}
