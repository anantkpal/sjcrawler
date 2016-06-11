package in.anantkpal.sjcrawler.validator;

/**
  @author Anant Pal
*/
import in.anantkpal.sjcrawler.validator.IValidator;
import in.anantkpal.sjcrawler.page.Page;


import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  This is the implementation for the class for the cross domian validation
*/
public class CrossDomainValidator implements IValidator{

  /**
    This is the crawler logger
  */
  Logger logger = LoggerFactory.getLogger(CrossDomainValidator.class);
    /**
      Root URL
    */
    private URL rootURL;

  /**
    Constructir that creates the instance of the CrossDomainValidator
  */
  public CrossDomainValidator(URL rootURL){
    this.rootURL = rootURL;
  }

  /**
    validator implementation that compares the root with child.
    Needs to implement as part of the interface IValidate
    @param page this is the page that needs to be validated
    @return If the URL can be navigated than retrun true or else false.
  */
  public boolean validate(final Page page){
      logger.debug("Validating url {}  while base is {}",page.getLink(),this.rootURL);
      return page.getLink().getHost().equalsIgnoreCase(this.rootURL.getHost());
  }

}
