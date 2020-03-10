package StepDefination_CucumberBDD;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import BaseUnit.App_Lib;
import PageObjectClass.ContactPage;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefination extends App_Lib {
	
	//Page Class Object
	//=================================================
	
	ContactPage oCP;
	
	WebDriver driver;
	Logger logger;
	TakesScreenshot ts;
	
	@Before
	public void initialiseTest(Scenario scenario) {
		
		
	   logger = Logger.getLogger(StepDefination.class);
	   PropertyConfigurator.configure("log4j2.properties");
	   
	   
	   String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());//time stamp
	   logger.info("=======================================  HEADER ======================================");
	   logger.info("Scenario :- "+scenario.getName()+" at :- "+System.getProperty("user.dir"));
	   logger.info("Executed By :- "+System.getProperty("user.name")+" on "+timeStamp);
	   logger.info("    * OS Name :- "+System.getProperty("os.name")+" v"+System.getProperty("os.version"));
	   logger.info("    * JVM Name :- "+System.getProperty("java.vm.name")+" v"+System.getProperty("java.vm.version"));
	   logger.info("    * JAVA Home Directory :- "+System.getProperty("java.home")+" v"+System.getProperty("java.runtime.version"));
	   logger.info("======================================================================================");
	   logger.info(" Executing the TestCases");
	   /*logger.debug("This is a debug message");
       logger.info("This is an info message");
       logger.warn("This is a warn message");
       logger.error("This is an error message");
       logger.fatal("This is a fatal message");*/
	}
	
	@After
	public void endTest(Scenario scenario) throws IOException, InterruptedException {
		if(scenario.isFailed()) {
			try {
				logger.info("");
				logger.error(scenario.getName()+" is Failed");
				logger.info("=========================================================");
				logger.fatal("Closing the Scenario : "+scenario.getName());
				logger.info("=========================================================");
				String timeStamp = new SimpleDateFormat("ddMMyy_HHmmss").format(new Date());//time stamp
				ts = (TakesScreenshot) driver;
				final byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
				File screenshot_with_scenario_name = ts.getScreenshotAs(OutputType.FILE);			
				File target = new File(System.getProperty("user.dir") + "/Screenshots/" + scenario.getName() + "_"+timeStamp+"_error.png");
				FileUtils.copyFile(screenshot_with_scenario_name, target);
				scenario.embed(screenshot, "image/png");
			}catch(WebDriverException somePlatformsDontSupportScreenshots) {
				System.err.println(somePlatformsDontSupportScreenshots.getMessage());
			}finally{
				Thread.sleep(5000);
			  	driver.quit();
			}
		}
		else {
			logger.info("=========================================================");
			logger.info("End of Scenario : "+scenario.getName());
			logger.info("=========================================================");
			logger.info(" ");
			logger.info(" ");
			Thread.sleep(5000);
		  	driver.quit();
			
		}
	}

    @Given("^User is already on login page$")
    public void user_already_on_login_page() throws IOException, InterruptedException {
    	logger.info("Setting Up the Browser");
    	driver = browserSetup();
    	
    	oCP = new ContactPage(driver);
    }
    
    @When("^title of the page is Free CRM$")
    public void title_of_page_is_Free_CRM() throws IOException {
    	
    
    	   String title = driver.getTitle();
    	   logger.info("Verifying the title of the Web Page");
    	   if(title.equalsIgnoreCase("Cogmento CRM")==true) {
    		   logger.info(" * WebPage Title is as expected. Hence verification successful.");
    	   }
    	   else {
    		   logger.warn(" (!) WebPage Title is not as expected. Hence verification failed.");
    	   }
    	   Assert.assertEquals(title,"Cogmento CRM"); 
    }
    
    @Then("^user enters username and password$")
    public void user_enter_username_and_password() throws IOException {
    	logger.info("Entering user name and password");
 	    driver.findElement(By.name("email")).sendKeys(getConfigData("UserName"));
 	    driver.findElement(By.name("password")).sendKeys(getPassword());
    }
    @Then("^user clicks on the login button$")
    public void user_clicks_on_the_login_button() {
    	logger.info("Clicking on the login Button");
    	driver.findElement(By.xpath("//div[text()='Login']")).click();
    }
    @Then("^user in on the home page$")
    public void user_on_home_page() {
    	
 	   String homepage_title = driver.getTitle();
 	   logger.info("Verifying the title of the Application Page");
 	    if(homepage_title.equalsIgnoreCase("Cogmento CRM")==true) {
		   logger.info(" * WebPage Title is as expected. Hence verification successful.");
	 	}
	 	else {
	 	   logger.warn(" (!) WebPage Title is not as expected. Hence verification failed.");
	 	}
 	   Assert.assertEquals(homepage_title,"Cogmento CRM"); 
     }
    @Then("^user creates a new Contact$")
    public void user_create_new_contact() throws InterruptedException {
    	Thread.sleep(10000);
    	logger.info("Creating new contact");
    	driver.findElement(By.xpath(".//a[@href='/contacts']")).click();
    	driver.findElement(By.xpath(".//*[text()='New']")).click();
      }
    @Then("^user enters \"([^\"]*)\" , \"([^\"]*)\" and \"([^\"]*)\" .$")
    public void user_enters_credentials(String Firstname,String Lastname,String Category) throws InterruptedException {
    	Thread.sleep(5000);
    	logger.info("Entering the contact credentials to create a contact");
    	oCP.userContactCredentialsEnter(Firstname, Lastname, Category);
    	
    	logger.info("Saving the recently created contact");
    	oCP.saveContact(); 
    	Thread.sleep(5000);
    	
    	logger.info("Fetching the created Contact Full Name");
    	String ContactName = oCP.contactNameFetch();
    	
    	if(ContactName.equalsIgnoreCase(Firstname+" "+Lastname)==true) {
 		   logger.info(" * WebPage Title is as expected. Hence verification successful.");
	 	}
	 	else {
	 	   logger.warn(" (!) WebPage Title is not as expected. Hence verification failed.");
	 	}
    	Assert.assertEquals(Firstname+" "+Lastname,ContactName); 
    	//Assert.assertEquals(ContactName,Firstname+" Ghosh");
      }
    @Then("^user removes the contact and close the browser$")
    public void user_remove_contact_and_close_browser() throws InterruptedException {
    	logger.info("Removing the contacts");
    	driver.findElement(By.xpath(".//*[@class='trash icon']")).click();
    	driver.findElement(By.xpath(".//*[text()='Delete']")).click();
  	   
      }
    
    @Then("^user chooses different options$")
    public void user_chooses_different_options() throws InterruptedException {
    	Thread.sleep(10000);
    	logger.info("Clicking on Contacts Option");
    	driver.findElement(By.xpath(".//a[@href='/contacts']")).click();
    	    	
    	logger.info("Clicking on Companies Option");
    	driver.findElement(By.xpath(".//a[@href='/companies']")).click();
    	
    	logger.info("Clicking on Deals Option");
    	driver.findElement(By.xpath(".//a[@href='/deals']")).click();
    	
    	logger.info("Clicking on Tasks Option");
    	driver.findElement(By.xpath(".//a[@href='/tasks']")).click();
    	
    	logger.info("Clicking on Cases Option");
    	driver.findElement(By.xpath(".//a[@href='/cases']")).click();
    	
    	logger.info("Clicking on Calls Option");
    	driver.findElement(By.xpath(".//a[@href='/calls']")).click();
    	
    	logger.info("Clicking on Documents Option");
    	driver.findElement(By.xpath(".//a[@href='/documents']")).click();
    	
    	logger.info("Clicking on Email Option");
    	driver.findElement(By.xpath(".//a[@href='/email']")).click();
    	
    	logger.info("Clicking on Campaigns Option");
    	driver.findElement(By.xpath(".//a[@href='/campaigns']")).click();
    	
    	logger.info("Clicking on Form Option");
    	driver.findElement(By.xpath(".//a[@href='/forms']")).click();
    }
      
      @Then("^user logs out of the Application$")
      public void user_logs_out_Application() throws InterruptedException {
        	logger.info("Logging Out of the Application");
        	Thread.sleep(5000);
        	driver.findElement(By.xpath("//*[@id='top-header-menu']/div[3]/div[2]/div/i")).click();
        	driver.findElement(By.xpath(".//*[text()='Log Out']")).click();
        	
          }
}
