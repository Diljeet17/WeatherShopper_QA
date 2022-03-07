package base;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {

	public static WebDriver driver;
	
	public static void initialization(String browserName){
		String chromeDriverPath = "./drivers/chromedriver.exe";
		String geckoDriverPath = "./drivers/geckodriver.exe";
		if(browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			ChromeOptions options = new ChromeOptions();
			options.setPageLoadStrategy(PageLoadStrategy.NONE); // https://www.skptricks.com/2018/08/timed-out-receiving-message-from-renderer-selenium.html
			options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
			options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
			options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
			options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
			options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
			options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
			options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc
			driver = new ChromeDriver(options); 
		}
		else if(browserName.equals("firefox")){
			System.setProperty("webdriver.gecko.driver", geckoDriverPath);	
			driver = new FirefoxDriver(); 
		}
		
		
		/*
		 * e_driver = new EventFiringWebDriver(driver); // Now create object of
		 * EventListerHandler to register it with EventFiringWebDriver eventListener =
		 * new WebEventListener(); e_driver.register(eventListener); driver = e_driver;
		 */
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://weathershopper.pythonanywhere.com/");
		
	}
	
	public void explicitWait(By locator) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		
	}
	
	
}
