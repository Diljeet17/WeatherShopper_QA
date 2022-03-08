package tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
//import base.TestBase;
import pages.Checkout;
import pages.CommonElements;
import pages.Confirmation;
import pages.Home;
//import pages.TestBase;
import utils.JsonUtility;

public class WeatherShopperTest extends TestBase {
	
	Home homePage;
	CommonElements commonElements;
	Checkout checkoutPage;
	Confirmation confirmationPage;
	JsonUtility jsonUtility;

	
	/*
	 * @BeforeSuite public void setUp(String browser) { //initialization(browser);
	 * homePage = new Home(driver); checkoutPage = new Checkout(driver);
	 * commonElements = new CommonElements(driver); confirmationPage = new
	 * Confirmation(driver); jsonUtility = new JsonUtility(); }
	 */

	@Test(priority=1)
	public void weatherShopperTest() throws FileNotFoundException, IOException, ParseException, InterruptedException {
		
		homePage = new Home(driver);
		checkoutPage = new Checkout(driver);
		commonElements = new CommonElements(driver);
		confirmationPage = new Confirmation(driver);
		jsonUtility = new JsonUtility();
		
		int currentTemperature;
	    HashMap<String, String> leastExpensiveItem1Detail = null;
	    HashMap<String, String> leastExpensiveItem2Detail = null;
	    
	    //Fetching current temperature
		currentTemperature = homePage.getCurrentTemperature();
		
		/*
		 * If Current Temperature is below 19 degree celcius then click on ‘Buy
		 * Moisturizers’ button, if Current Temperature is above 34 degree celcius then
		 * click on ‘Buy Sunscreens’ button
		 */
		if(currentTemperature < 19) {
			
			 homePage.clickBuyMoisturizersBtn();
			
			 //Get least expensive item containing ‘aloe’ & least expensive item containing ‘almond’
			 leastExpensiveItem1Detail = commonElements.getLeastExpensiveItemDetails(jsonUtility.getData("moisturizer_aloe"));
			 leastExpensiveItem2Detail = commonElements.getLeastExpensiveItemDetails(jsonUtility.getData("moisturizer_almond"));
			
			 //Complete buy process with above two items
			 validateBuyFlow(leastExpensiveItem1Detail, leastExpensiveItem2Detail);			
		}
		else if(currentTemperature > 34) {
			homePage.clickBuySunscreensBtn();
			
			//Get least expensive item of type ‘SPF-50’ & then least expensive item of type ‘SPF-30’.
			leastExpensiveItem1Detail = commonElements.getLeastExpensiveItemDetails(jsonUtility.getData("sunscreen_spf50"));
			leastExpensiveItem2Detail = commonElements.getLeastExpensiveItemDetails(jsonUtility.getData("sunscreen_spf30"));
			
			//Complete buy process with above two items
			validateBuyFlow(leastExpensiveItem1Detail, leastExpensiveItem2Detail);
		}
	}
		
	/*
	 * This method adds items to Car, validates items appear on Checkout page as
	 * expected & then does payment process by entering email, credit card details &
	 * then checks for success message on Confirmation page
	 */
	public void validateBuyFlow(HashMap<String, String> leastExpensiveItem1Detail, HashMap<String, String> leastExpensiveItem2Detail) throws InterruptedException {
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
	    
	    //Get String values of Item1's Name & Price from HashMap object 
	    for(Entry<String, String> entry: leastExpensiveItem1Detail.entrySet()) {
			leastExpensiveItem1Name = entry.getKey();
			leastExpensiveItem1Price = Integer.parseInt(entry.getValue());
		}
		
	    //Get String values of Item2's Name & Price from HashMap object 
		for(Entry<String, String> entry: leastExpensiveItem2Detail.entrySet()) {
			leastExpensiveItem2Name = entry.getKey();
			leastExpensiveItem2Price = Integer.parseInt(entry.getValue());
		}
		
		//Calculating Actual total of two items added to Cart
		leastExpensiveItemsTotalAmount = leastExpensiveItem1Price + leastExpensiveItem2Price;
		
		//Click on Add button for Item1
		commonElements.clickAddBtn(leastExpensiveItem1Name);
		
		//Click on Add button for Item2
		commonElements.clickAddBtn(leastExpensiveItem2Name);
		
		//Click on Cart button from top right side 
		commonElements.clickCartBtn();
		
		//Get Item details from table on Checkout page
		ItemsOnCheckoutPage = checkoutPage.getItemDetails();
		
		//Get String values of Item's Name & Price from HashMap object 
		for(Entry<String, String> entry: ItemsOnCheckoutPage.entrySet()) {
			
			if(entry.getKey().equals(leastExpensiveItem1Name)) {
				checkoutTableItem1Name = entry.getKey();
				checkoutTableItem1Price = Integer.parseInt(entry.getValue());
			} else if(entry.getKey().equals(leastExpensiveItem2Name)) {
				checkoutTableItem2Name = entry.getKey();
				checkoutTableItem2Price = Integer.parseInt(entry.getValue());
			}
		}
		
		//Assertions for Item details like Name, Price & Total Amount on Checkout page
		Assert.assertEquals(checkoutTableItem1Name, leastExpensiveItem1Name);
		Assert.assertEquals(checkoutTableItem2Name, leastExpensiveItem2Name);
		Assert.assertEquals(checkoutTableItem1Price, leastExpensiveItem1Price);
		Assert.assertEquals(checkoutTableItem2Price, leastExpensiveItem2Price);
		Assert.assertEquals(Integer.parseInt(checkoutPage.getTotalRupees()), leastExpensiveItemsTotalAmount);
		
		//Click on 'Pay with Card' button
		checkoutPage.clickPayWithCardBtn();
		
		//Switch to Stripe popup window
		checkoutPage.switchToStripeFrame();
		
		//Enter Email & Credit Card details
		checkoutPage.enterEmail("test@gmail.com");
		checkoutPage.enterCreditCardDetails("4242424242424242", "10/25", "123", "54321");
		
		//Click on Pay button
		checkoutPage.clickPayBtn();
		
		//Switch back to parent frame
		checkoutPage.switchToParentFrame();
		
		/*
		 * Verify success message, there are 5% chances of being failure, so if failure
		 * message appears then following assertion will fail
		 */
		Assert.assertEquals(confirmationPage.getMessage(), "Your payment was successful. You should receive a follow-up call from our sales team.");
	}
}
