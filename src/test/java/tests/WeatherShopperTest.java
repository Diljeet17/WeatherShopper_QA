package tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.HashMap;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.TestBase;
import pages.Checkout;
import pages.CommonElements;
import pages.Confirmation;
import pages.Home;

public class WeatherShopperTest extends TestBase {
	
	Home homePage;
	CommonElements commonElements;
	Checkout checkoutPage;
	Confirmation confirmationPage;

	
	@BeforeMethod
	@Parameters("browser")
	public void setUp(String browser) {
		initialization(browser);
		homePage = new Home();
		checkoutPage = new Checkout();
		commonElements = new CommonElements();
		confirmationPage = new Confirmation();
		
	}

	@Test(priority=1)
	public void weatherShopperTest() {
		
		int currentTemperature;
	    HashMap<String, String> leastExpensiveItem1Detail = null;
	    HashMap<String, String> leastExpensiveItem2Detail = null;
	    
	    //Fetching current temperature
		currentTemperature = homePage.getCurrentTemperature();
		System.out.println("CT "+currentTemperature);
		
		//Checking whether current temperature is below 19 or above 34
		if(currentTemperature < 19) {
			 homePage.clickBuyMoisturizersBtn();
			
			 leastExpensiveItem1Detail = commonElements.getLeastExpensiveItem("Aloe");
			 leastExpensiveItem2Detail = commonElements.getLeastExpensiveItem("almond");
				
			 validateBuyFlow(leastExpensiveItem1Detail, leastExpensiveItem2Detail);			
		}
		else if(currentTemperature > 34) {
			homePage.clickBuySunscreensBtn();
			
			leastExpensiveItem1Detail = commonElements.getLeastExpensiveItem("SPF-50");
			leastExpensiveItem2Detail = commonElements.getLeastExpensiveItem("SPF-30");
			
			validateBuyFlow(leastExpensiveItem1Detail, leastExpensiveItem2Detail);
		}
	}
	
	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
	
	public void validateBuyFlow(HashMap<String, String> leastExpensiveItem1Detail, HashMap<String, String> leastExpensiveItem2Detail) {
		String leastExpensiveItem1Name = null;
	    int leastExpensiveItem1Price = 0;
	    String leastExpensiveItem2Name = null;
	    int leastExpensiveItem2Price = 0;
	    int leastExpensiveItemsTotalAmount = 0;
	    HashMap<String, String> ItemsOnCheckoutPage = new HashMap<String, String>();
	    String checkoutTableItem1Name = null;
	    int checkoutTableItem1Price = 0;
	    String checkoutTableItem2Name = null;
	    int checkoutTableItem2Price = 0;
	    
	    
	    for(Entry<String, String> entry: leastExpensiveItem1Detail.entrySet()) {
			leastExpensiveItem1Name = entry.getKey();
			leastExpensiveItem1Price = Integer.parseInt(entry.getValue());
		}
		
		for(Entry<String, String> entry: leastExpensiveItem2Detail.entrySet()) {
			leastExpensiveItem2Name = entry.getKey();
			leastExpensiveItem2Price = Integer.parseInt(entry.getValue());
		}
		leastExpensiveItemsTotalAmount = leastExpensiveItem1Price + leastExpensiveItem2Price;
		commonElements.clickAddBtn(leastExpensiveItem1Name);
		commonElements.clickAddBtn(leastExpensiveItem2Name);
		commonElements.clickCartBtn();
		
		ItemsOnCheckoutPage = checkoutPage.getItemDetails();
		
		for(Entry<String, String> entry: ItemsOnCheckoutPage.entrySet()) {
			
			if(entry.getKey().equals(leastExpensiveItem1Name)) {
				checkoutTableItem1Name = entry.getKey();
				checkoutTableItem1Price = Integer.parseInt(entry.getValue());
			} else if(entry.getKey().equals(leastExpensiveItem2Name)) {
				checkoutTableItem2Name = entry.getKey();
				checkoutTableItem2Price = Integer.parseInt(entry.getValue());
			}
		}
		
		Assert.assertEquals(checkoutTableItem1Name, leastExpensiveItem1Name);
		Assert.assertEquals(checkoutTableItem2Name, leastExpensiveItem2Name);
		Assert.assertEquals(checkoutTableItem1Price, leastExpensiveItem1Price);
		Assert.assertEquals(checkoutTableItem2Price, leastExpensiveItem2Price);
		Assert.assertEquals(Integer.parseInt(checkoutPage.getTotalRupees()), leastExpensiveItemsTotalAmount);
		
		checkoutPage.clickPayWithCardBtn();
		checkoutPage.switchToStripeFrame();
		checkoutPage.enterEmail("test@gmail.com");
		checkoutPage.enterCreditCardDetails("4242424242424242", "10/25", "123", "54321");
		checkoutPage.clickPayBtn();
		checkoutPage.switchToParentFrame();
		
		Assert.assertEquals(confirmationPage.getSuccessMessage(), "Your payment was successful. You should receive a follow-up call from our sales team.");
		
	}
}
