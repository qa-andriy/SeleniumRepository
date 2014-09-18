package com.selenium.atf.core.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.selenium.atf.core.web.WebComponent;

public class Image extends WebComponent <Image>{

	public Image(WebDriver driver, By findByMethod) {
		super(driver, findByMethod);
	}
	
	public Image checkImagePresents(){
		getWebElement().isDisplayed();
		return this;
	}
}
