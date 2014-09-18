package com.selenium.atf.core.web.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.selenium.atf.core.web.WebPage;
import com.selenium.atf.core.web.elements.Button;
import com.selenium.atf.core.web.elements.DropDownList;
import com.selenium.atf.core.web.elements.Table;
import com.selenium.atf.core.web.elements.TextInput;
import com.selenium.atf.core.web.page.InvoicePage;

public class InvoicePage extends WebPage<InvoicePage> {
	
	private static final String PAGE_URL = BASE_URL + "/index.html";

	public InvoicePage(WebDriver driver) {
		super(driver);
	}

	@Override
	public InvoicePage load() {
		driver.get(PAGE_URL);
		return this;
	}

	public InvoicePage LoadPage() {
		load();
		return this.waitUntilAvailable();
	}

// checking of available Main elements
	@Override
	public boolean isAvailable() {
		return getMainProductNameInput().waitUntilAvailable().isAvailable()
				&& getMainUnitsSelect().waitUntilAvailable().isAvailable()
				&& getMainQuantityInput().waitUntilAvailable().isAvailable()
				&& getMainPriceInput().waitUntilAvailable().isAvailable()
				&& getMainAddProductButton().waitUntilAvailable().isAvailable();
	}

	public InvoicePage AddNewProduct(String ProductName, String Units,
			String Quantity, String Price) {
		getMainProductNameInput().inputText(ProductName);
		getMainUnitsSelect().selectUnit(Units);
		getMainQuantityInput().inputText(Quantity);
		getMainPriceInput().inputText(Price);
		getMainAddProductButton().click();
		return new InvoicePage(driver).waitUntilAvailable();
	}

	public Table getProductsTable() {
		return new Table(driver, By.xpath("//*[@class='rows-wrapper']"));
	}

	
// Searching main fields
	private TextInput getMainProductNameInput() {
		return new TextInput(driver, By.id("product-name"));
	}

	private DropDownList getMainUnitsSelect() {
		return new DropDownList(driver, By.id("units"));
	}

	private TextInput getMainQuantityInput() {
		return new TextInput(driver, By.id("quantity"));
	}

	private TextInput getMainPriceInput() {
		return new TextInput(driver, By.id("price"));
	}

	private Button getMainAddProductButton() {
		return new Button(driver, By.id("add-button"));
	}

	
// Total Price from Invoice table
	private TextInput getTotalPriceFromInvoiceTable() {
		return new TextInput(driver,
				By.xpath(".//*[@class='tr-cell units summary']"));
	}

	public String getTotalPriceFieldTextFromInvoiceTable() {
		return getTotalPriceFromInvoiceTable().getText();
	}


// Get text from Main fields Product name, quantity and price empty

	public String getTextMainProductNameField() {
		return getMainProductNameInput().getTextValue();
	}

	public String getTextMainQuantityField() {
		return getMainQuantityInput().getTextValue();
	}

	public String getTextMainPriceField() {
		return getMainPriceInput().getTextValue();
	}

	
// Get Classes of Main elements for equals
	public String getMainProductNameFieldClass() {
		return getMainProductNameInput().getId();
	}

	public String getMainQuantityFieldClass() {
		return getMainQuantityInput().getId();
	}

	public String getMainPriceFieldClass() {
		return getMainPriceInput().getId();
	}

	
// Check is buttons present on page
	public boolean isAddProductButtonPresents() {
		return getAddProductButton().isAvailable();
	}

	
// Searching Add Button
	private Button getAddProductButton() {
		return new Button(driver, By.xpath(".//*[@src='images/add.png']"));
	}
}
