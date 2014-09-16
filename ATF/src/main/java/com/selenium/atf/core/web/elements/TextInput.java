package com.selenium.atf.core.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.selenium.atf.core.web.WebComponent;

public class TextInput extends WebComponent<TextInput> {

	public TextInput(WebDriver driver, By findByMethod) {
		super(driver, findByMethod);
	}

	public TextInput inputText(String text) {
		getWebElement().sendKeys(text);
		return this;
	}

	public TextInput clearText() {
		getWebElement().clear();
		return this;
	}

	public String getTextValue() {
		return getWebElement().getAttribute("value");
	}

	public String getText() {
		return getWebElement().getText();
	}
}
