# Simple Java Web Crawler

This is the simple JAVA libarary to crawl pages which you can embed in your existing systems


## How to use it

```java
      
      //Creating the settings instance
      Settings settings = new Settings();

      //Creating factory instance
      CrawlerFactory factory = new CrawlerFactory();
      //You can write your own factory extending `CrawlerFactory` but need to make sure `getCrawlerInstance` signature must be met

      //Validators - These are clases which implement IValidator which helps us to check whether URL needs to be crawled or not.
      //Must have validator is CrossDomainValidator
      CrossDomainValidator vali = CrossDomainValidator(new URL("http://www.anantkpal.in"));

      //Add this validator to our settings instance
      settings.addCanNavigateValidator(vali);

      //Lets create the instance of the crawler
      SJCrawler mainCrawler = new SJCrawler(factory);

      //configuring the crawler
      mainCrawler.configure("http://www.anantkpal.in",settings);

      //starting the crawler
      mainCrawler.startCrawl()

      //get links and tree struture that has been processed
      List<Page> pages = mainCrawler.getPages();


###CrawlerFactory

This is the important class that retruns  ICrawler. You can implement your own crawler by implementing  ICrawler. I have implemented only one crawler using jsoup

###Documentation

Please find the javadoc in docs folder
