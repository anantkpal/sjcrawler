package in.anantkpal.sjcrawler.validator;

/**
  @author Anant Pal
*/
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import in.anantkpal.sjcrawler.page.Page;
import in.anantkpal.sjcrawler.validator.CrossDomainValidator;


import java.net.URL;

/**
  This is the test class for the CrossDomainValidator Validator
*/
public class CrossDomainValidatorTest{

  private Page page;
  private CrossDomainValidator validator;

  @Before
  public void setUp(){
    try{
      page = new Page(new URL("http://www.anantkpal.in"));
    }catch(Exception e){}

    //setting the depth of the page
    page.setDepth(2);

    try{
      //creating the instance of the CrossDomainValidator
      validator = new CrossDomainValidator(new URL("http://www.anantkpal.in"));
    }catch(Exception e){}
  }

  @Test
  public void NOCrossDomain(){
    assertEquals(true,validator.validate(page));
  }


  @Test
  public void crossdomain(){
    //set to cross max
    try{
      page = new Page(new URL("http://www.google.com"));
    }catch(Exception e){}

    assertEquals(false,validator.validate(page));
  }

}
