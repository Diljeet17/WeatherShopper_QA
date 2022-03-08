package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Confirmation extends BasePage{
	
    WebDriver driver;
	By message = By.xpath("//p[@class='text-justify']");
	
	public Confirmation(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	
	//Method to returns success/failure message
	public String getMessage() {
		return driver.findElement(message).getText();
	}
}
