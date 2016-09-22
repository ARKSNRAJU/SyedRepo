package com.sample.common_actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Reporter;

import com.sample.utils.Utlities;

public class CommonMethods {
	public static WebDriver driver;
	public static Properties prop=null;
	public static FileInputStream propFis;
	public static HashMap<String, String> propertiesMap;
	private static HashMap<String, String> readConfigData;
	
	/*
	 * This method will open the required browser and navigate to the application
	 * @author Raju
	 */
	public static void invokeApp(){
		readConfigData=Utlities.readProperties(".\\properties\\config.properties");
		if(readConfigData.get("browserName").toLowerCase().equals("ie")){
			System.setProperty("webdriver.ie.driver", ".\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		else if(readConfigData.get("browserName").toLowerCase().equals("chrome")){ 
			System.setProperty("webdriver.chrome.driver", ".\\drivers\\ChromeDriver.exe");
			driver = new ChromeDriver();
		}
		else if(readConfigData.get("browserName").toLowerCase().equals("firefox")){
			driver = new FirefoxDriver();
		}
		
		driver.manage().window().maximize();
		logMessage("Navigating to :"+readConfigData.get("url"));
		driver.get(readConfigData.get("url"));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	/*
	 * This method is use for enter text in particular text box
	 * @author Raju
	 * @param locator, value, valueToEnter, msg
	 */
	public static void enterText(String locator, String value, String valueToEnter, String msg){
			findElementBy(locator, value).clear();
			findElementBy(locator, value).sendKeys(valueToEnter);
			logMessage(valueToEnter+" is entered in "+msg);
	}
	
	/*
	 * This method is use for enter text in particular text box
	 * @author Raju
	 * @param locator, value
	 * @return WebElement
	 */
	public static WebElement findElementBy(String locator, String value){
		WebElement webElement=null;
		switch(locator.toLowerCase()){
		case "id":
			webElement = driver.findElement(By.id(value));
			break;
			
		case "name":
			webElement = driver.findElement(By.name(value));
			break;	
			
		case "classname":
			webElement = driver.findElement(By.className(value));
			break;	
			
		case "xpath":
			webElement = driver.findElement(By.xpath(value));
			break;
			
		case "linktext":
			webElement = driver.findElement(By.linkText(value));
			break;	
		}
		return webElement;
	}
	
	public static String readPropertyFile(String fileName, String property) {
		prop = new Properties();
		try {
			propFis = new FileInputStream(new File(".\\properties\\config.properties"));
			prop.load(propFis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(property);
	}
	
	/*
	 * This method will click on the element
	 * @author Raju
	 * @param locator, value, msg
	 */
	public static void click(String locator, String value, String msg){
		findElementBy(locator, value).click();
		logMessage("Clicked on "+msg);
	}
	
	/*
	 * This method will verify actual and expected text
	 * @author Raju
	 * @param locator, value, expMessage, msg
	 */
	public static void verifyText(String locator, String value , String expMessage, String msg){
		if(findElementBy(locator, value).getText().trim().contains(expMessage)){
			logMessage(msg+"displayed -Pass");
		}else{
			logErrorMessage(msg+"not displayed -Fail");
		}
	}
	
	/*
	 * This method will verify an element is displayed or not
	 * @author Raju
	 * @param locator, value
	 * @return boolean
	 */
	public static boolean isElementDisplayed(String locator, String value){
		return findElementBy(locator, value).isDisplayed();
	}
	
	/*
	 * This method is use for print text in console and Reporter output in TestNG reports
	 * @author Raju
	 * @param msg
	 */
	public static void logMessage(String msg){
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("<br>"+msg);
		System.out.println(msg);
	}
	
	/*
	 * This method is use for print text in console and Reporter output in TestNG reports in red color
	 * @author Raju
	 * @param msg
	 */
	public static void logErrorMessage(String msg){
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("<font color='red'></br>"+msg+"</font>");
		takeSnap(msg);
		System.out.println(msg);
		assert false;
	}
	
	/*
	 * This method is use to take snap shot
	 * @author Raju
	 * @param name
	 */
	public static void takeSnap(String name) {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(".\\snaps\\"+name+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This method is use to delete the folder
	 * @author Raju
	 * @param folderName
	 */
	public static void deleteFolder(String folderName){
		try {
			FileUtils.deleteDirectory(new File(folderName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This method will close the browser along with sessions
	 * @author Raju
	 */
	public static void quit(){
		driver.quit();
		logMessage("Closed the browser");
	}
}
