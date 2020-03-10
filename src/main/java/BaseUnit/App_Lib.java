package BaseUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import cucumber.api.Scenario;

public class App_Lib {
	
	WebDriver driver;
	TakesScreenshot ts;
	public WebDriver browserSetup() throws IOException, InterruptedException {
		String URL = getConfigData("baseURL");
		String BROWSER = getConfigData("BROWSER");
		
		if(BROWSER.equalsIgnoreCase("Chrome")){
    	    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+getConfigData("ChromeDriverPath"));
    	    driver = new ChromeDriver();
    	}
    	else if(BROWSER.equalsIgnoreCase("IE")){
    		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+getConfigData("IEDriverPath"));
	    	driver = new InternetExplorerDriver();
    	}
    	else if(BROWSER.equalsIgnoreCase("Firefox")){
    		System.setProperty("webdriver.firefox.driver", System.getProperty("user.dir")+getConfigData("FirefoxDriverPath"));
	    	driver = new InternetExplorerDriver();
    	}
    	   Thread.sleep(5000);
    	   driver.manage().deleteAllCookies();
    	   Thread.sleep(10000);
    	   driver.get(URL);
    	   driver.manage().window().maximize();
		return driver;
	}
	
	public String getPassword() throws IOException {	
		File src = new File(System.getProperty("user.dir")+"\\Configuration\\config.properties");
		FileInputStream fis = new FileInputStream(src);
		Properties prop = new Properties();
		prop.load(fis);
		String passWord = prop.getProperty("Password");
		/*byte[] encodedPassword = Base64.encodeBase64(passWord.getBytes());
		System.out.println("Encoded Password is : "+ new String(encodedPassword));*/
		
		byte[] decodedPassword = Base64.decodeBase64(passWord);
		
		return new String(decodedPassword);
	}
	
	public String getConfigData(String DataItem) throws IOException {
		
		File src = new File(System.getProperty("user.dir")+"\\Configuration\\config.properties");
		FileInputStream fis = new FileInputStream(src);
		Properties prop = new Properties();
		prop.load(fis);
		String DataValue = prop.getProperty(DataItem);
		
		return DataValue;
	}
	
	public String takeScreenShot(Scenario scenario,WebDriver driver) throws IOException {
		String timeStamp = new SimpleDateFormat("ScreenShot_ddMMyyyy_HHmmss").format(new Date());//time stamp
		ts = (TakesScreenshot) driver;
		final byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
		File screenshot_with_scenario_name = ts.getScreenshotAs(OutputType.FILE);
		String sc_FileName = timeStamp+".png";
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" +sc_FileName);
		FileUtils.copyFile(screenshot_with_scenario_name, target);
		scenario.embed(screenshot, "image/png");
		return sc_FileName;
	}
	

}
