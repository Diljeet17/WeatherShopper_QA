package tests;

import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;
	
	@Parameters("browser")
	@BeforeClass
	public void initialization(String browserName){
		
		switch(browserName.toLowerCase()) {
		
		case "chrome": {
		    	// Using WebDriverManager as it removes the chromedriver version dependency
		    	WebDriverManager.chromedriver().setup();
		    	ChromeOptions options = new ChromeOptions();
		    	options.setPageLoadStrategy(PageLoadStrategy.NONE); 
		    	options.addArguments("start-maximized"); 
		    	options.addArguments("enable-automation"); 
		    	options.addArguments("--no-sandbox"); 
		    	options.addArguments("--disable-infobars"); 
		    	options.addArguments("--disable-dev-shm-usage"); 
				options.addArguments("--disable-browser-side-navigation"); 
				options.addArguments("--disable-gpu"); 
				driver = new ChromeDriver(options);
				break;
		     }
		    
		    case "firefox": {
		    	// Using WebDriverManager as it removes the chromedriver version dependency
		    	WebDriverManager.firefoxdriver().setup();
		    	driver = new FirefoxDriver();
		    	break;
		     }
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		
		driver.get("https://weathershopper.pythonanywhere.com/");
	}
	
	//Method to close browser
	@AfterTest
	public void closeBrowser() {
		driver.close();
	}	
}
