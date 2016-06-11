package in.anantkpal.sjcrawler.page;

/**
  @author Anant Pal (anantkpal@yahoo.co.in)
*/

import java.util.List;
import java.util.ArrayList;

import java.net.URL;

/**
  Simple Page Class representing the attributes of the page
*/
public class Page {


    /**
      Page title
    */
    private String title;
    /**
      This is the URL of the page
    */
    private URL link;

    /**
      This is the parent page of the object
    */
    private List<Page> parents = new ArrayList<Page>();

    /**
      These are the child pages of the current page
    */
    private List<Page> childPages = new ArrayList<Page>();

    /**
      This is the depth of the page
    */
    private int depth = 0;


    /**
      This is seq of the page
    */
    private int seq;


    /**
      Setter for the seq
      @param seq This is the sequence number for the page
    */
    public void setSeq(int seq){
      this.seq = seq;
    }

    /**
      Getter for the sequence
      @return sequence number of the page
    */
    public int getSeq(){
      return this.seq;
    }


    /**
      @param link This is thevery important attribute of the page
    */
    public Page(URL link){
      this.link = link;
    }


    /**
      Setter to set the page title
      @param title This is the title of the page that needs to set
    */
    public void setTitle(String title){
      this.title = title;
    }


    /**
      Getter to get the page title
      @return Page Title as String
    */
    public String getTitle(){
      return this.title;
    }

    /**
      Setter to set the depth of the page
      @param depth This is depth in integer
    */
    public void setDepth(int depth){
        this.depth = depth;
    }

    /**
      Getter to get the depth of the page
      @return depth of the page in integer
    */
    public int getDepth(){
      return this.depth;
    }


    /**
      Setter for the parent
      @param parent page
    */
    public void addParent(Page parent){
      this.parents.add(parent);
    }


    /**
      Getter for the parent
      @return Parents of this element if null for root element
    */
    public List<Page> getParents(){
      return this.parents;
    }


    /**
      Getter for the link object
      @return Link of the page
    */
    public URL getLink(){
      return this.link;
    }

    /**
      Adding the child page to the parent
      @param page The page that needs to be registered tothe parent
    */
    public void addChildPage(Page page){
      this.childPages.add(page);
    }

    /**
      To get the child pages
      @return Child pages
    */
    public List<Page> getChildPages(){
      return this.childPages;
    }


    /**
      This is to override the equals method of the Object
      @param o Object that needs to be compared with
    */
    @Override
    public boolean equals(Object o){
        if(o == null)                return false;
        if(!(o instanceof Page))     return false;

        Page other = (Page) o;
        if(! this.link.equals(other.getLink()))   return false;
        return true;
  }


  /**
    Overriding the hashcode method of the page
    @return has code integer
  */
  @Override
  public int hashCode(){
    return  this.link.hashCode();
  }

}
