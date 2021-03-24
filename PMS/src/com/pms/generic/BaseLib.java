package com.pms.generic;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.pms.pageobject.BasePage;
import com.pms.pageobject.CreateAccountPage;
import com.pms.pageobject.GoogleAPIPage;







public class BaseLib {
	public WebDriver driver;
	protected CreateAccountPage createAccountPage;
	protected GoogleAPIPage googleApiPage;

	@BeforeMethod(alwaysRun =false, groups = "Amazon")
	@Parameters({"browser"})
	public void preConditionWeb(String browsername){
		 final Logger logger = LogManager.getLogger(BaseLib.class);
		 logger.trace("entering into application");
		 
		 if(browsername.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "/var/www/mn-testing/PMS/Exe Files/geckodriver");
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("dom.webnotifications.enabled", false);
			profile.setPreference("geo.enabled", false);
			String Node = "http://10.0.2.83:6666/wd/hub";
	 		DesiredCapabilities cap = DesiredCapabilities.firefox();
	 
	 		try {
				driver = new RemoteWebDriver(new URL(Node), cap);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			Reporter.log("Firefox Browser launches");
		}
		else if (browsername.equalsIgnoreCase("chrome")  )
		{
		//	WebDriverManager.chromedriver().version("71.0.3578.80").setup();
			System.setProperty("webdriver.chrome.driver", "/var/www/mn-testing/PMS/Exe Files/chromedriver");
			ChromeOptions options = new ChromeOptions();
			options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
	                  UnexpectedAlertBehaviour.IGNORE);
			options.addArguments("disable-geolocation");
			options.addArguments("--disable-notifications");
			options.addArguments("--no-sandbox");
			driver = new ChromeDriver(options);
			Reporter.log("Chrome Browser launches");
		}
		
			driver.manage().window().maximize();
			driver.get(GetPropertyValues.getPropertyValue("amazonURL"));
			Reporter.log("Navigate to the URL", true);
			BasePage.sleepForMilliSecond(2000);
			
			
			createAccountPage = PageFactory.initElements(driver, CreateAccountPage.class);
			googleApiPage = PageFactory.initElements(driver, GoogleAPIPage.class);
	
		}
	
	
	
	@BeforeMethod(alwaysRun=false, groups = "googleapi")
	public void preconditionapi() {
		googleApiPage = PageFactory.initElements(driver, GoogleAPIPage.class);
	}
	
	
	@AfterMethod(alwaysRun =false, groups = "Amazon")
	public void postCondition(ITestResult result)
	{
		if (result.isSuccess())
		{
				Reporter.log("Script passed",true);
		}
		else
		{
		    Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		    String browserName = cap.getBrowserName().toLowerCase().toString();
			String filename = result.getMethod().getMethodName();
			SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");  
			Date date = new Date();
			String chromeFolder = "/var/www/mn-testing/PMS/Screenshot/Chrome/" + formatter.format(date);  
			File file1 = new File(chromeFolder);
			if(!file1.exists()){
				file1.mkdir();
			}
			ScreenShotLib sLib= new ScreenShotLib();
			sLib.getScreenShot(driver, filename, browserName, chromeFolder);
			Reporter.log(filename + " has beeen failed and Screenshot has been taken",true);
		}
		driver.quit();
		Reporter.log("Browser closed",true);
	}
}
	


 