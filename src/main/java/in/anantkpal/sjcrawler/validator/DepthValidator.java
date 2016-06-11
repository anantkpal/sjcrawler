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
  This is the implementation for the class for the validating the depth of the Page that can be crawled
*/
public class DepthValidator implements IValidator{

  /**
    This is the crawler logger
  */
  Logger logger = LoggerFactory.getLogger(DepthValidator.class);

    /**
    This is the max depth
    */
    private int maxDepth;

  /**
    Constructor that creates the instance of the DepthValidator
  */
  public DepthValidator(int maxDepth){
    this.maxDepth = maxDepth;
  }

  /**
    validator implementation that compares the root with child.
    Needs to implement as part of the interface IValidate
    @param page this is the page that needs to be validated
    @return If the URL can be navigated than retrun true or else false.
  */
  public boolean validate(final Page page){
      logger.debug("Validating depth {}",page.getDepth());
      return page.getDepth() < this.maxDepth;
  }

}
