package com.pCloudy.testNg;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class Runner {

	WebDriver driver;
	DesiredCapabilities capabilities ;
	String folder_name;
	DateFormat df;

	@BeforeTest
	public void setUpSuite() throws Exception {

	}

	@Parameters({"os","osVersion","browserName","browserVersions"})
	@BeforeMethod
	public void prepareTest(String os,String osVersion,String browserName,String browserVersions) throws IOException, InterruptedException {
		capabilities = new DesiredCapabilities();
		if (browserName.contains("chrome")) {
			capabilities.setCapability("browserName", "chrome");
		} else if (browserName.contains("firefox")) {
			capabilities.setCapability("browserName", "firefox");
		} else if (browserName.contains("edge")) {
			capabilities.setCapability("browserName", "edge");
		}
		capabilities.setCapability("os", os);
		capabilities.setCapability("osVersion", osVersion);
		capabilities.setCapability("browserVersion",browserVersions);
		capabilities.setCapability("enableWildnet",true);
		capabilities.setCapability("clientName", "d.titus@zensar.com");
		capabilities.setCapability("apiKey", "3xkdrfjjctb8v9fy5nchpyfy");
		capabilities.setCapability("email", "d.titus@zensar.com");
		driver = new RemoteWebDriver(new URL("https://browser.device.pcloudy.com/wd/hub/seleniumcloud/wd/hub"), capabilities);

	}


	@Test
	public void Test() throws Exception {

		WebDriverWait wait=new WebDriverWait(driver, 20);
		driver.manage().window().maximize();
		driver.get("http://google.com/");
		System.out.println(driver.getTitle());
		captureScreenShots();

	}


	@AfterMethod
	public void afterMethod() throws InterruptedException{

		driver.quit();

	}

	public void captureScreenShots() throws Exception{

		folder_name="screenshot";
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		File f=scrShot.getScreenshotAs(OutputType.FILE);
		df=new  SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		//create dir with given folder name
		new File(folder_name).mkdir();
		//Setting file name
		String file_name=df.format(new Date())+".png";
		//copy screenshot file into screenshot folder.
		FileHandler.copy(f, new File(folder_name + "/" + file_name));

	}
}
