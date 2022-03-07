package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.openqa.selenium.By;
import base.TestBase;

/*This class addressess common elements present on Moisturizers 
& Sunscreens page & possible operation with respect to them*/
public class CommonElements extends TestBase {
	
    By cart = By.xpath("//button[@class='thin-text nav-link']");
	
    //Method to click on Cart button present on top right side of Moisturizers/Sunscreens page
	public void clickCartBtn() {
		explicitWait(cart);
		driver.findElement(cart).click();
	}
	
	//Method to click Add button based on Item Name
	public void clickAddBtn(String itemName) {
		driver.findElement(By.xpath("//button[contains(@onclick,'"+itemName+"')]")).click();
	}
	
	//Method to get least expensive item details
	public HashMap<String, String> getLeastExpensiveItemDetails(String criteria) {
		
		HashMap<String, String> itemAndPriceMap = new HashMap<String, String>();
		List <String> specificItemNames = new ArrayList<>();
		List<String> specificItemPrices = new ArrayList<>();
		HashMap<String, String> leastExpensiveItemAndPriceMap = new HashMap<String, String>();
		int minimumPriceIndex;
		
		//Adding all items present on Moisturizers/Sunscreens page in HashMap object
		for(int i=1; i<=3;i++) {
			itemAndPriceMap.put(driver.findElement(By.xpath("//div[2]/div["+i+"]/p[1]")).getText(), driver.findElement(By.xpath("//div[2]/div["+i+"]/p[2]")).getText());
			itemAndPriceMap.put(driver.findElement(By.xpath("//div[3]/div["+i+"]/p[1]")).getText(), driver.findElement(By.xpath("//div[3]/div["+i+"]/p[2]")).getText());
		}
		
		//filtering items based on criteria & storing in list object
		for(Entry<String, String> entry: itemAndPriceMap.entrySet()) {

			if(entry.getKey().toLowerCase().contains(criteria.toLowerCase())) {
				specificItemNames.add(entry.getKey());
				specificItemPrices.add(entry.getValue().replace("Price:","").replace("Rs.","").replace(" ",""));
			}
		}
		
		//finding index of item which has minimum price
		minimumPriceIndex = specificItemPrices.indexOf(Collections.min(specificItemPrices));
		//adding least expensive item name & price in HashMap object
		leastExpensiveItemAndPriceMap.put(specificItemNames.get(minimumPriceIndex), specificItemPrices.get(minimumPriceIndex));
		return leastExpensiveItemAndPriceMap;
	}
}
