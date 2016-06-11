package in.anantkpal.sjcrawler.validator;

/**
  @author Anant Pal (anantkpal@yahoo.co.in)
*/

import in.anantkpal.sjcrawler.page.Page;

/**
  Interface that should be implemented by validator
*/
public interface IValidator{

  /**
    validator implementation that compares the root with child
    @param page Page that needs to be validated
    @return If the Page can be navigated than retrun true or else false.
  */
  public boolean validate(final Page page);

}
