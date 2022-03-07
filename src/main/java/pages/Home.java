package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import base.TestBase;

public class Home extends TestBase {
	
	By temperature = By.id("temperature");
	By buyMoisturizersButton = By.xpath("//a[@href='/moisturizer']");
	By buySunscreensButton = By.xpath("//a[@href='/sunscreen']");
	
	//Method to get current temperature
	public int getCurrentTemperature() {
		String temperatureText;
		int temp;
		temperatureText = driver.findElement(temperature).getText();
		temperatureText = temperatureText.replace("\u00B0","").replace("\u2103","").replace("C","").replace(" ","");
		temp = Integer.parseInt(temperatureText);
		return temp;
	}
	
	//Method to click 'Buy Moisturizers' button
	public void clickBuyMoisturizersBtn() {
		WebElement btn;
		/*
		 * Sometimes this button generates StaleElementReferenceException, hence
		 * hanlding with try-catch block
		 */
		try {
			btn = driver.findElement(buyMoisturizersButton);
			btn.click();
		} catch(StaleElementReferenceException e) {
			btn = driver.findElement(buyMoisturizersButton);
			btn.click();	
		}
	}
	
	//Method to click 'Buy Suncreens' button
    public void clickBuySunscreensBtn() {
    	WebElement btn;
    	/*
		 * Sometimes this button generates StaleElementReferenceException, hence
		 * hanlding with try-catch block
		 */
		try {
			btn = driver.findElement(buySunscreensButton);
			btn.click();
		} catch(StaleElementReferenceException e) {
			btn = driver.findElement(buySunscreensButton);
			btn.click();	
		}
	}
}
