package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.TestBase;

public class Home extends TestBase {

	//private WebDriver driver;
	//private WebElement temperature;
	//private WebElement buyMoisturizersButton;
	//private WebElement buySunscreensButton;
	By temperature = By.id("temperature");
	By buyMoisturizersButton = By.xpath("//a[@href='/moisturizer']");
	By buySunscreensButton = By.xpath("//a[@href='/sunscreen']");
	
	
	public int getCurrentTemperature() {
		String temperatureText;
		int temp;
		temperatureText = driver.findElement(temperature).getText();
		temperatureText = temperatureText.replace("\u00B0","").replace("\u2103","").replace("C","").replace(" ","");
		temp = Integer.parseInt(temperatureText);
		return temp;
	}
	
	public void clickBuyMoisturizersBtn() {
	   driver.findElement(buyMoisturizersButton).click();
	}
	
    public void clickBuySunscreensBtn() {
    	driver.findElement(buySunscreensButton).click();
	}
}
