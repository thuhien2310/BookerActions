package actions;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.TimeUtils;

public class BaseTest 
{
 
	WebDriver driver;
	public static final String myURL = "https://dev.tripi.vn";
   
	@BeforeMethod
	public void beforeTest() throws Exception 
	{
		driver = new FirefoxDriver();
		driver.manage().window().maximize() ;
		driver.get(myURL);
		//TimeUtils.sleep(5);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void afterTest() 
	{
		driver.quit();
	}	
}


