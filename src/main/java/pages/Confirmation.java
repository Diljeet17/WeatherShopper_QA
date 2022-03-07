package pages;

import org.openqa.selenium.By;

import base.TestBase;

public class Confirmation extends TestBase {

	By successMessage = By.xpath("//p[@class='text-justify']");
	
	public String getSuccessMessage() {
		return driver.findElement(successMessage).getText();
	}
}
