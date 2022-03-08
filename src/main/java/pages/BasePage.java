package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	
	  WebDriver driver;
	  WebDriverWait wait;
	  
	  public BasePage(WebDriver driver) { 
		  this.driver=driver; 
		  
	  }
	 
	  //This method helps in explicitly waiting for given element to be clickable
	  public void waitForElementToBeClickable(By locator) {
		  wait = new WebDriverWait(driver,Duration.ofSeconds(15));
		  wait.until(ExpectedConditions.elementToBeClickable(locator));
	  }
	  
	  //This method helps in explicitly waiting for element to be visible
	  public void waitForElementVisibility(By locator) {
		  wait = new WebDriverWait(driver,Duration.ofSeconds(15));
		  wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
	  }
}
