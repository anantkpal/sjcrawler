package in.anantkpal.sjcrawler.validator;

/**
  @author Anant Pal
*/
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import in.anantkpal.sjcrawler.page.Page;
import in.anantkpal.sjcrawler.validator.DepthValidator;


import java.net.URL;

/**
  This is the test class for the DepthValidator Validator
*/
public class DepthValidatorTest{

  private Page page;
  private DepthValidator validator;

  @Before
  public void setUp(){
    try{
      page = new Page(new URL("http://www.anantkpal.in"));
    }catch(Exception e){}

    //setting the depth of the page
    page.setDepth(2);

    //creating the instance of the DepthValidator
    validator = new DepthValidator(3);
  }

  @Test
  public void validateMax(){
    assertEquals(true,validator.validate(page));
  }


  @Test
  public void validateForFailure(){
    //set to cross max
    page.setDepth(7);

    assertEquals(false,validator.validate(page));
  }

}
