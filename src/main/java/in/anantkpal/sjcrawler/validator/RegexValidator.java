package in.anantkpal.sjcrawler.validator;

/**
  @author Anant Pal
*/
import in.anantkpal.sjcrawler.validator.IValidator;
import in.anantkpal.sjcrawler.page.Page;

import java.net.URL;


import java.util.regex.Pattern;


/**
  This is the implementation for the class for the cross domian validation
*/
public class RegexValidator implements IValidator{


  /**
    Root URL
  */
  private Pattern regexPattern;

  /**
    Constructir that creates the instance of the RegexValidator
    @param regexPattern This is the pattern that will be validated
  */
  public RegexValidator(String regexPattern){
    this.regexPattern = Pattern.compile(regexPattern);
  }

  /**
    validator implementation that compares the root with child.
    Needs to implement as part of the interface IValidate
    @param page Page that needs to be validated
    @return If the URL can be navigated than retrun true or else false.
  */
  public boolean validate(final Page page){
      return this.regexPattern.matcher(page.getLink().toString()).matches();
  }

}
