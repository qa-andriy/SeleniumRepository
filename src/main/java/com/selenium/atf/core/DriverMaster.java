package com.selenium.atf.core;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverMaster {
	
	private static BrowserType BROWSER;
	private static Map<String, WebDriver> driversMap = new HashMap<String, WebDriver>();
	
	//Constructor-blocker, it won't be possible to create instance of DriverMaster
	private DriverMaster(){};
	
	public static WebDriver getDriver(String driverKey){
		if (driversMap.containsKey(driverKey)){
			return driversMap.get(driverKey);
		} else {
			driversMap.put(driverKey, createDriver());
			return driversMap.get(driverKey);
		}
	}
	
	public static void quitDriver(String driverKey){
		WebDriver driver = driversMap.remove(driverKey);
		driver.quit();
	}
	
	public static void setBrowser(String browserKey){
		BROWSER = BrowserType.get(browserKey);
	}
	
	public static WebDriver createDriver(){
		WebDriver driver;
		switch (BROWSER) {
		case FIREFOX:
			driver = new FirefoxDriver();
			break;
		case CHROME:
			System.setProperty("webdriver.chrome.driver", "C:/workspace/_Libraries/drivers/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case IE:
			System.setProperty("webdriver.ie.driver", "C:/workspace/_Libraries/drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			break;

		default:
			driver = new FirefoxDriver();
			break;
		}
		return driver;
	}
	
}
