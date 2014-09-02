package com.selenium.atf.core;

import static com.selenium.atf.core.DriverMaster.getDriver;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class ScreenshotOnTestFailure extends TestListenerAdapter {

	@Override
	public void onTestFailure(ITestResult tr) {
		WebDriver driver = getDriver(tr.getTestClass().getName());
		File srcFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		String destDir = "target/surefire-reports/screenshots";
		new File(destDir).mkdirs();
		String destFile = new Date().getTime() + ".png";
		try {
			FileUtils.copyFile(srcFile, new File(destDir + "/" + destFile));
		} catch (IOException e) {
		}
		String linkPath = "./screenshots/" + destFile;
		Reporter.log("<a href='" + linkPath + "'><img src='" + linkPath
				+ "'style='width: 120px; height: 50px;' /></a>");
		super.onTestFailure(tr);
	}
}
