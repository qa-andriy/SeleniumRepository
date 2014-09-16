package com.selenium.atf.core.web;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class WebComponent<T extends WebComponent<T>> extends Component<T> {

	protected final By findByMethod;
	protected final WebElement element;
	
	public WebComponent(WebDriver driver, By findByMethod) {
		super(driver);
		this.findByMethod = findByMethod;
		this.element = null;
	}
	
	public WebComponent(WebDriver driver, WebElement element) {
		super(driver);
		this.findByMethod = null;
		this.element = element;
	}
	
	@Override
	public boolean isAvailable(){
    	try{
        	return getWebElement() != null; 	
    	} catch (NoSuchElementException e){
    		return false;
    	}
    }
    
	//click element
	public void click(){
		getWebElement().click();
	}
	
	//get text from element
	public String getText(){
		return getWebElement().getText();
	}
	
	//get class of element
	public String getId(){
		String id = getWebElement().getAttribute("class");
		return id;
	}
	
	public WebElement getWebElement(){
		if(this.element != null){
			return this.element;
		}
    	return	driver.findElement(findByMethod);
    }
}
