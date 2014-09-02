package com.selenium.atf.core.web;

import org.openqa.selenium.WebDriver;

public abstract class WebPage<T extends WebPage<T>> extends Component<T> {

	// sending driver to WebPage for working
	public WebPage(WebDriver driver) {
		super(driver);
	}

	public abstract T load();

	public T LoadAndWaitUntilAvailable() {
		T page = load();
		waitUntilAvailable();
		return page;
	}
}
