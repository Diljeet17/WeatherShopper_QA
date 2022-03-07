package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;

import base.TestBase;

public class CommonElements extends TestBase {

    By cart = By.xpath("//button[@class='thin-text nav-link']");
	
	public void clickCartBtn() {
		explicitWait(cart);
		driver.findElement(cart).click();
	}
	
	public void clickAddBtn(String itemName) {
		driver.findElement(By.xpath("//button[contains(@onclick,'"+itemName+"')]")).click();
	}
	
	public HashMap<String, String> getLeastExpensiveItem(String condition) {
		HashMap<String, String> itemAndPriceMap = new HashMap<String, String>();
		List <String> specificItemNames = new ArrayList<>();
		List<String> specificItemPrices = new ArrayList<>();
		HashMap<String, String> leastExpensiveItemAndPriceMap = new HashMap<String, String>();
		int minimumPriceIndex;
		
		for(int i=1; i<=3;i++) {
			itemAndPriceMap.put(driver.findElement(By.xpath("//div[2]/div["+i+"]/p[1]")).getText(), driver.findElement(By.xpath("//div[2]/div["+i+"]/p[2]")).getText());
			itemAndPriceMap.put(driver.findElement(By.xpath("//div[3]/div["+i+"]/p[1]")).getText(), driver.findElement(By.xpath("//div[3]/div["+i+"]/p[2]")).getText());
		}
		
		for(Entry<String, String> entry: itemAndPriceMap.entrySet()) {

			if(entry.getKey().toLowerCase().contains(condition.toLowerCase())) {
				specificItemNames.add(entry.getKey());
				specificItemPrices.add(entry.getValue().replace("Price:","").replace("Rs.","").replace(" ",""));
			}
		      
		}
		
		minimumPriceIndex = specificItemPrices.indexOf(Collections.min(specificItemPrices));
		
		leastExpensiveItemAndPriceMap.put(specificItemNames.get(minimumPriceIndex), specificItemPrices.get(minimumPriceIndex));
		return leastExpensiveItemAndPriceMap;
	}
}
