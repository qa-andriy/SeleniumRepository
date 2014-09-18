package com.selenium.atf.core.web.elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.selenium.atf.core.web.WebComponent;

public class DropDownList extends WebComponent<DropDownList> {

	public DropDownList(WebDriver driver, By findByMethod) {
		super(driver, findByMethod);
	}

	public DropDownList selectUnit(String text) {
		Select unitItem = new Select(getWebElement());
		unitItem.selectByVisibleText(text);
		return this;
	}

	public List<String> getAvailableOptions() {
		Select unitItem = new Select(getWebElement());
		List<String> options = new ArrayList<String>();
		for (WebElement el : unitItem.getOptions()) {
			options.add(el.getText());
		}
		return options;
	}
}
