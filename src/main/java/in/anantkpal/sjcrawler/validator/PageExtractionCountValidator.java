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
  This is the implementation for the class for the validating the number of pages that needs to transvese havae been met or not
*/
public class PageExtractionCountValidator implements IValidator{

  /**
    This is the crawler logger
  */
  Logger logger = LoggerFactory.getLogger(PageExtractionCountValidator.class);

    /**
    This is the max count
    */
    private int maxCount;

  /**
    Constructir that creates the instance of the CrossDomainValidator
  */
  public PageExtractionCountValidator(int maxCount){
    this.maxCount = maxCount;
  }

  /**
    validator implementation that compares the root with child.
    Needs to implement as part of the interface IValidate
    @param page this is the page that needs to be validated
    @return If the URL can be navigated than retrun true or else false.
  */
  public boolean validate(final Page page){
      logger.debug("Validating max count {}",page.getSeq());
      return page.getSeq() < this.maxCount;
  }

}
