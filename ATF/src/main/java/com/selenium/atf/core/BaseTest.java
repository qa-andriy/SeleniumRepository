package com.selenium.atf.core;

import static com.selenium.atf.core.DriverMaster.getDriver;
import static com.selenium.atf.core.DriverMaster.setBrowser;
import static com.selenium.atf.core.DriverMaster.quitDriver;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import static com.selenium.atf.core.Configuration.setGlobalEnvironment;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;

public abstract class BaseTest {

	protected WebDriver driver;

	@BeforeSuite(alwaysRun = true)
	@Parameters({ "browser", "environment" })
	public void setGlobalBrowser(@Optional("firefox") String browser, @Optional("local") String environment) {
		setBrowser(browser);
		setGlobalEnvironment(environment);
	}

	@BeforeClass(alwaysRun = true)
	public void SetUp() {
		driver = getDriver(this.getClass().getName());
	}

	@AfterClass(alwaysRun = true)
	public void TearDown() {
		quitDriver(this.getClass().getName());
	}
}
