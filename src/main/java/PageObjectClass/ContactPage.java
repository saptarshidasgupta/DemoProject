package PageObjectClass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ContactPage {

WebDriver driver;
	
	public ContactPage(WebDriver baseDriver) {
		driver = baseDriver;
	}
	

	//================================================
	//Parameters
	//================================================	
	String sUserName;
	
	
	//================================================
	//X-path
	//================================================	
	
	By firstname = By.xpath(".//label[text()='First Name']//following::input");
	By lastname = By.xpath(".//label[text()='Last Name']//following::input");
	By dropdown = By.xpath(".//label[text()='Category']//following::div");
	By catagory = By.xpath(".//label[text()='Category']//following::div");
	By saveBtn = By.xpath(".//button[text()='Save']");
	By contactFullName = By.xpath(".//*[@class='ui header item mb5 light-black']");
	//================================================
	//Methods
	//================================================	
	public void userContactCredentialsEnter(String sFirstName, String sLastName, String sPosition) throws InterruptedException
	{
		
		    //Enter First Name
		    WebElement oFirstName=driver.findElement(firstname);
		    oFirstName.sendKeys(sFirstName);
		    //Enter Last Name
		    WebElement oLastName=driver.findElement(lastname);
		    oLastName.sendKeys(sLastName);
		  //Click on the drop down Button
		    WebElement oDropdown=driver.findElement(dropdown);
		    oDropdown.click();
		    
		    Thread.sleep(5000);
		  //Select the Position
		    WebElement oPosition=driver.findElement(By.xpath(".//label[text()='Category']//following::span[text()='"+sPosition+"']"));
		    oPosition.click();
		    
	}
	
	public void saveContact() { 
	        //Click on the save button
			WebElement oSaveButton=driver.findElement(saveBtn);
			oSaveButton.click();
	}
	
	public String contactNameFetch() { 
        //Click on the save button
		WebElement oContactFullName=driver.findElement(contactFullName);
		return oContactFullName.getText();
}
	
	
}
