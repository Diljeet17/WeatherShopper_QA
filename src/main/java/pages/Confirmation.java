package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.TestBase;

public class Confirmation extends TestBase {
	
	By message = By.xpath("//p[@class='text-justify']");
	
	//Method to returns success/failure message
	public String getMessage() {
		return driver.findElement(message).getText();
	}
}
