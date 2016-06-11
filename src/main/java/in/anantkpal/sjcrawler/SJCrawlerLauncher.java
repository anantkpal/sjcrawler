package in.anantkpal.sjcrawler;

/**
  @author Anant Pal
*/
import in.anantkpal.sjcrawler.Settings;
import in.anantkpal.sjcrawler.crawler.CrawlerFactory;
import in.anantkpal.sjcrawler.validator.CrossDomainValidator;
import in.anantkpal.sjcrawler.validator.DepthValidator;
import in.anantkpal.sjcrawler.validator.PageExtractionCountValidator;
import in.anantkpal.sjcrawler.validator.RegexValidator;
import in.anantkpal.sjcrawler.SJCrawler;
import in.anantkpal.sjcrawler.page.Page;

import java.net.URL;

import com.sanityinc.jargs.CmdLineParser;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.File;

import java.util.List;

/**
  This is the launcher class which is responsible for command line run
*/
public class SJCrawlerLauncher{

  private static final String VERSION = "1.0.0Beta";

  /**
    Main function responsible for the run the jar
  */
  public static void main(String[] args){

      CmdLineParser parser = new CmdLineParser();

      //url switch --url {url param}
      CmdLineParser.Option urlOpt = parser.addStringOption('u',"url");

      //version switch --version or -v
      CmdLineParser.Option versionOpt = parser.addBooleanOption('V', "version");

      // help switch --help or -h
      CmdLineParser.Option helpOpt = parser.addBooleanOption('h', "help");

      //Output file to which links needs to be returned -o or --output
      CmdLineParser.Option outputFileOpt = parser.addStringOption('o', "output");

      //Engine that can be used for crawling the page -e or --engine
      CmdLineParser.Option engineOpt = parser.addStringOption('e', "engine");

      //Number of threads crawler can use to crawl -t or --threads
      CmdLineParser.Option threadsOpt = parser.addStringOption('t', "threads");

      //Number of pages to be crawled switch --pages
      CmdLineParser.Option pagesOpt = parser.addStringOption("pages");

      //Depath to which pages can be crawled --depth
      CmdLineParser.Option depthOpt = parser.addStringOption("depth");

      //Regex for the pages that can be crawled --regex
      CmdLineParser.Option regexOpt = parser.addStringOption("regex");

      //Disable cross domain validator, this is in case if you want to navigat throught the doamins which doesn't belong to the rootURL --dCD
      CmdLineParser.Option dCDOpt = parser.addBooleanOption("dCD");

      try{
          parser.parse(args);

          //Version Display if Args is Version
          Boolean version = (Boolean) parser.getOptionValue(versionOpt);
          if (version != null && version.booleanValue()) {
              version();
              System.exit(0);
          }

          //Help Display if args is Help
          Boolean help = (Boolean) parser.getOptionValue(helpOpt);
          if (help != null && help.booleanValue()) {
              usage();
              System.exit(0);
          }


          //reading the URL
          String url = (String) parser.getOptionValue(urlOpt);

          //Output File to which links can be written
          String outputFile = (String) parser.getOptionValue(outputFileOpt);

          //Both are mandatory or else exist with usage message
          if (outputFile == null || url == null) {
              usage();
              System.exit(1);
          }

          //Create the instance of the settings which we can feed tothe sjcrawler while configuring
          Settings settings = new Settings();

          //Create the crawler factory instance.
          CrawlerFactory factory = new CrawlerFactory();

          //Crawler initialisation
          String crawlerType = (String) parser.getOptionValue(engineOpt);
          if(crawlerType != null){
            settings.setCrawlerType(crawlerType);
          }

          //no of threads
          String noOfThreads = (String) parser.getOptionValue(threadsOpt);
          if(noOfThreads != null){
            settings.setNoOfThreads(Integer.parseInt(noOfThreads));
          }

          //depth configuration
          String depth = (String) parser.getOptionValue(depthOpt);
          if(depth != null){
            DepthValidator dValidator = new DepthValidator(Integer.parseInt(depth));
            settings.addCanNavigateValidator(dValidator);
          }

          //pages count configuration
          String pages = (String) parser.getOptionValue(pagesOpt);
          if(pages != null){
            PageExtractionCountValidator pValidator = new PageExtractionCountValidator(Integer.parseInt(pages));
            settings.addCanNavigateValidator(pValidator);
          }


          //regex configuration
          String regex = (String) parser.getOptionValue(regexOpt);
          if(regex != null){
            RegexValidator regexValidator = new RegexValidator(regex);
            settings.addCanNavigateValidator(regexValidator);
          }

          //Disabling cross domain validator
          Boolean disableCD = (Boolean) parser.getOptionValue(dCDOpt);
          if (disableCD != null && disableCD.booleanValue()) {
              //If disable do nothing :)
          }else{
            //If not disable enable validator
            CrossDomainValidator cvalidatr = new CrossDomainValidator(new URL(url));
            settings.addCanNavigateValidator(cvalidatr);
          }

          //Simple Java Crawler Instance creation
          SJCrawler crawler = new SJCrawler(factory);

          //configuring the crawler
          crawler.configure(url,settings);

          //Start the Crawler
          crawler.startCrawl();


          //save the links to the file
          File fout = new File(outputFile);
	        FileOutputStream fos = new FileOutputStream(fout);
	        OutputStreamWriter osw = new OutputStreamWriter(fos);

          //List Pages
          List<Page> allPages = crawler.getPages();
          for (Page paze : allPages) {
		           osw.write(paze.getLink().toString()+"\n");
	        }
	        osw.close();

          System.out.println("-----------------------------------------------------------------------------------------------");

      }catch (CmdLineParser.OptionException e) {
            usage();
            System.exit(1);
      }catch(Exception e){

      }

  }


  /**
    Prints the usage information of the Jar
  */
  private static void usage(){
    System.err.println(
              "sjcrawler Version: "+VERSION+"\n"

                      + "\nUsage: java -jar sjcrawler-all-"+VERSION+".jar [options] -u <URL that needs to be crawled> -o <File to which links to be written>\n"
                      + "\n"
                      + "Global Options\n"
                      + "  -V, --version                          Print version information\n"
                      + "  -h, --help                             Displays this information\n"
                      + "  -u, --url                              URL from which you need to start crawling.\n"
                      + "  -o <file>                              Place the where all the links are dumped in <file>.\n"
                      + "  -e, --engine <engine name>             Crawler Engine that will be used for crawling (Currently supports only JSoup).\n"
                      + "  -t, --threads <Number>                 <Number> threads crawler can use of the JVM\n"
                      + "  --dCD                                  Enable crsoss domain navigation.\n"
                      + "  --pages  <No of Pages>                 Number of pages that needs to crawled.\n"
                      + "  --depth  <Depth Number>                Depth to which page can be crawled.\n"
                      + "  --regex  <Regex expression>            Regex validator for the URL that can be navigated\n\n");
  }

  /**
    Prints theversion information for the jar
  */
  private static void version(){
    System.out.println("sjcrawler "+VERSION);
  }

}
