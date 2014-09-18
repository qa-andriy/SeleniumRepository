package com.selenium.atf.core.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.selenium.atf.core.web.WebComponent;

public class Link extends WebComponent <Link>{

	public Link(WebDriver driver, By findByMethod) {
		super(driver, findByMethod);
	}
}
