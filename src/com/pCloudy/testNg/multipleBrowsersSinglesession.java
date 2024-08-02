package com.pCloudy.testNg;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class multipleBrowsersSinglesession {
    WebDriver driver;
    DesiredCapabilities capabilities ;
    String folder_name;
    DateFormat df;
    String os = null;
    String browser = null;
    @Test
    public void Test() throws Exception {
        settingCapabilties("chrome","Mac","Ventura", "120");
        TestScript();
        waitForSessionRelease();
        settingCapabilties("edge","Windows","11", "118");
        TestScript();
        waitForSessionRelease();
        settingCapabilties("firefox","Mac","Ventura", "113");
        TestScript();
        waitForSessionRelease();
        try {
            settingCapabilties("safari", "Mac", "Monterey", "16");
        }catch(Exception e){
            settingCapabilties("safari", "Mac", "Monterey", "15");
        }
        TestScript();
    }

    @AfterMethod
    public void afterMethod() throws InterruptedException{
        if(driver != null)
            driver.quit();
    }
    public void captureScreenShots() throws Exception{
        folder_name= "screenshots";
        TakesScreenshot scrShot =((TakesScreenshot)driver);
        File f=scrShot.getScreenshotAs(OutputType.FILE);
        df=new SimpleDateFormat("dd-MMM-yy__hh_mm_ssaa");
        new File(folder_name).mkdir();
        String file_name=os+"_"+browser+"_"+df.format(new Date())+".png";
        FileHandler.copy(f, new File(folder_name + "/" + file_name));
        System.out.println("Screenshot captured successfully and saved as: "+file_name+".png in folder : "+folder_name);

    }
    public void settingCapabilties(String browserName, String os, String osVersion, String browserVersion) throws MalformedURLException {
        this.os =os;
        this.browser = browserName;
        capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName",browserName);
        HashMap<String, Object> pcloudyOptions = new HashMap<String, Object>();
        pcloudyOptions.put("userName", "d.titus@zensar.com");
        pcloudyOptions.put("accessKey", "3xkdrfjjctb8v9fy5nchpyfy");
        pcloudyOptions.put("os", os);
        pcloudyOptions.put("osVersion", osVersion);
        pcloudyOptions.put("browserVersion", browserVersion);
        pcloudyOptions.put("clientName", "d.titus@zensar.com");
        pcloudyOptions.put("seleniumVersion", "3.141.59");
        pcloudyOptions.put("local", false);
        capabilities.setCapability("pcloudy:options", pcloudyOptions);
        System.out.println("Trying to Intiate browser: "+browser);
        driver = new RemoteWebDriver(new URL("https://browser.device.pcloudy.com/seleniumcloud/wd/hub"), capabilities);
    }
    public void TestScript() throws Exception {
        System.out.println("Browser Initiation Successfully");
        driver.get("http://google.com/");
        System.out.println("The current Page title : "+driver.getTitle());
        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
        System.out.println("Browser Window Maximized successfully");
        System.out.println("The current Page title : "+driver.getTitle());
        driver.findElement(By.id("firstName")).sendKeys("Noothan");
        driver.findElement(By.id("lastName")).sendKeys("sai");
        driver.findElement(By.id("userEmail")).sendKeys("nagasai@gmail.com");
        System.out.println("firstName, lastname, userEmail entered successfully");
        captureScreenShots();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,300)");
        Thread.sleep(200);
        driver.findElement(By.xpath("(//div/label[@class='custom-control-label'])[1]")).click();
        System.out.println("Selection of gender : "+driver.findElement(By.xpath("//input[@value='Male']")).isSelected());
        captureScreenShots();
        driver.findElement(By.xpath("//div[@id='userNumber-wrapper']/div[2]/input")).sendKeys("9874083830");
        System.out.println(driver.findElement(By.cssSelector("div[id='userNumber-wrapper'] div:nth-child(2)")).getText());
        captureScreenShots();
        driver.findElement(By.xpath("//input[@id='subjectsInput']")).sendKeys("subject");
        System.out.println("subject Entered successfully");
        captureScreenShots();
        driver.quit();
        System.out.println("Closing the Browser: "+ browser+" Successfullly");
    }
    public void waitForSessionRelease() throws InterruptedException {
        System.out.println("Waiting 150 seconds for the session to Release");
        Thread.sleep(160000);
        System.out.println("Waiting For session release completed");
    }
}
