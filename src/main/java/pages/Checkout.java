package pages;

import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import base.TestBase;

public class Checkout extends TestBase {
   
   By payWithCard = By.xpath("//button[@type='submit']");
   By totalRupees = By.xpath("//p[@id='total']");
   By email = By.xpath("//*[@id=\"email\"]");
   By creditCardNumber = By.id("card_number");
   By ccExpiry = By.id("cc-exp");
   By ccCVV = By.id("cc-csc");
   By zip = By.id("billing-zip");
   By payBtn = By.id("submitButton");
   
   //Method to click 'Pay with Card' button
   public void clickPayWithCardBtn() {
		driver.findElement(payWithCard).click();
	}
	
   //Method to get Total Amount in rupees below Item details table
	public String getTotalRupees() {
		return driver.findElement(totalRupees).getText().replace("Total:","").replace("Rupees","").replace(" ","");
	}
	
	//Method to enter email on Stripe popup window
	public void enterEmail(String user_email) {
		explicitWait(email);
		driver.findElement(email).sendKeys(user_email);
	}
	
	//Method to enter credit card details on Stripe popup window
	public void enterCreditCardDetails(String cc_number, String cc_expiry, String cc_cvv, String zipCode) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;  
		jsExecutor.executeScript("document.getElementById('card_number').value='"+cc_number+"'");
		jsExecutor.executeScript("document.getElementById('cc-exp').value='"+cc_expiry+"'");
		jsExecutor.executeScript("document.getElementById('cc-csc').value='"+cc_cvv+"'");
		jsExecutor.executeScript("document.getElementById('billing-zip').value='"+zipCode+"'");
	}
	
	//Method to click on Pay button on Stripe popup window
	public void clickPayBtn() {
		driver.findElement(payBtn).click();
	}
	
	//Method to return Item details from table on Checkout page
	public HashMap<String, String> getItemDetails() {
		HashMap<String, String> itemAndPriceMap = new HashMap<String, String>();
		
		int rowCount = driver.findElements(By.xpath("//table//tbody//tr")).size();
		for(int i=1;i<=rowCount; i++) {
			itemAndPriceMap.put(driver.findElement(By.xpath("//table//tbody//tr["+i+"]//td[1]")).getText(), driver.findElement(By.xpath("//table//tbody//tr["+i+"]//td[2]")).getText());
		}
		
		return itemAndPriceMap;
	}
	
	//Method to switch on Stripe popup window
	public void switchToStripeFrame() {
	      driver.switchTo().frame("stripe_checkout_app");
	}
	
	//Method to switch back to parent frame
	public void switchToParentFrame() {
	      driver.switchTo().parentFrame();
	}

}
