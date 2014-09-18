package com.selenium.atf.core.web;

import org.openqa.selenium.WebDriver;

import com.selenium.atf.core.Configuration;
import com.selenium.atf.core.Environment;

public abstract class WebPage<T extends WebPage<T>> extends Component<T> {
	
	private static final Configuration CONFIG = Configuration.getConfig();
	private static final Environment ENVIRONMENT = CONFIG.getEnvironmentSettings();
	protected static final String BASE_URL = ENVIRONMENT.scheme + "://" + ENVIRONMENT.host + ":" + ENVIRONMENT.port;

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
