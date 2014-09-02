package com.selenium.atf.core.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.selenium.atf.core.web.WebComponent;
import com.selenium.atf.core.web.page.InvoicePage;

public class Row extends WebComponent<Row> {

	public Row(WebDriver driver, WebElement element) {
		super(driver, element);
	}

	public InvoicePage EditAndAcceptAddedProduct(String ProductName,
			String Units, String Quantity, String Price) {
		getProductNameInEditModeInvoceTable().clearText()
				.inputText(ProductName);
		getUnitsInEditModeInvoceTableSelect().selectUnit(Units);
		getQuantityInEditModeInvoceTable().clearText().inputText(Quantity);
		getPriceInEditModeInvoceTable().clearText().inputText(Price);
		getAcceptChangesButton().click();
		return new InvoicePage(driver).waitUntilAvailable();
	}

	public InvoicePage EditAndCancelAddedProduct(String ProductName,
			String Units, String Quantity, String Price) {
		getProductNameInEditModeInvoceTable().clearText()
				.inputText(ProductName);
		getUnitsInEditModeInvoceTableSelect().selectUnit(Units);
		getQuantityInEditModeInvoceTable().clearText().inputText(Quantity);
		getPriceInEditModeInvoceTable().clearText().inputText(Price);
		getCancelChangesButton().click();
		return new InvoicePage(driver).waitUntilAvailable();
	}

	public String getNumber() {
		String number = getWebElement().findElement(
				By.xpath("./*[contains(@class, 'tr-cell number')]")).getText();
		return number;
	}

// ----------- ELEMENTS FROM INVOISE TABLE ----------------

	// Get text from edit fields from Invoice Table

	public String getTextTableNumberFieldInEdit() {
		return getNumberInEditModeInvoceTable().getText();
	}

	public String getTextTableProductNameFieldInEdit() {
		return getProductNameInEditModeInvoceTable().getTextValue();
	}

	public String getTextTableUnitsFieldInEdit() {
		return getUnitsInEditModeInvoceTableOptions().getText();
	}

	public String getTextTableQuantityFieldInEdit() {
		return getQuantityInEditModeInvoceTable().getTextValue();
	}

	public String getTextTablePriceFieldInEdit() {
		return getPriceInEditModeInvoceTable().getTextValue();
	}

	public String getTextTableTotalFieldInEdit() {
		return getTotalInEditModeInvoceTable().getText();
	}

	// Get Classes of elements for equals from Invoice Table

	public String getTableProductNumberFieldClass() {
		return getProductNumberFromInvoceTable().getId();
	}

	public String getTableProductNameFieldClass() {
		return getProductNameFromInvoceTable().getId();
	}

	public String getTableQuantityFieldClass() {
		return getQuantityFromInvoceTable().getId();
	}

	public String getTableUnitsFieldClass() {
		return getUnitsFromInvoceTable().getId();
	}

	public String getTablePriceFieldClass() {
		return getPriceFromInvoceTable().getId();
	}

	public String getTableTotalFieldClass() {
		return getTotalFromInvoceTable().getId();
	}

	// Check is Edit Mode classes presents in Invoice Table

	public String getTableProductNameFieldEditClass() {
		return getProductNameInEditModeInvoceTable().getId();
	}

	public String getTableUnitsFieldEditClass() {
		return getUnitsInEditModeInvoceTable().getId();
	}

	public String getTableQuantityFieldEditClass() {
		return getQuantityInEditModeInvoceTable().getId();
	}

	public String getTablePriceFieldEditClass() {
		return getPriceInEditModeInvoceTable().getId();
	}

	public String getTableTotalFieldEditClass() {
		return getTotalInEditModeInvoceTable().getId();
	}

	// Get Text from fields from Invoice Table

	public String getNumberFieldTextFromInvoiceTable() {
		return getWebElement().findElement(
				By.xpath("./*[contains(@class, 'tr-cell number')]")).getText();
	}

	public String getProductNameFieldTextFromInvoiceTable() {
		return getWebElement().findElement(
				By.xpath("./*[contains(@class, 'tr-cell product')]")).getText();
	}

	public String getUnitsFieldTextFromInvoiceTable() {
		return getWebElement().findElement(
				By.xpath("./*[contains(@class, 'tr-cell units')]")).getText();
	}

	public String getQuantityFieldTextFromInvoiceTable() {
		return getWebElement().findElement(
				By.xpath("./*[contains(@class, 'tr-cell quantity')]"))
				.getText();
	}

	public String getPriceFieldTextFromInvoiceTable() {
		return getWebElement().findElement(
				By.xpath("./*[contains(@class, 'tr-cell price')]")).getText();
	}

	public String getTotalFieldTextFromInvoiceTable() {
		return getWebElement().findElement(
				By.xpath("./*[contains(@class, 'tr-cell total')]")).getText();
	}

	// Searching Text Elements in Invoice Table (Added product)

	private TextInput getProductNumberFromInvoceTable() {
		return new TextInput(driver, By.xpath(".//*[@class='tr-cell number']"));
	}

	private TextInput getProductNameFromInvoceTable() {
		return new TextInput(driver, By.xpath(".//*[@class='tr-cell product']"));
	}

	private TextInput getUnitsFromInvoceTable() {
		return new TextInput(driver, By.xpath(".//*[@class='tr-cell units']"));
	}

	private TextInput getQuantityFromInvoceTable() {
		return new TextInput(driver,
				By.xpath(".//*[@class='tr-cell quantity']"));
	}

	private TextInput getPriceFromInvoceTable() {
		return new TextInput(driver, By.xpath(".//*[@class='tr-cell price']"));
	}

	private TextInput getTotalFromInvoceTable() {
		return new TextInput(driver, By.xpath(".//*[@class='tr-cell total']"));
	}

	// Searching Elements in edit mode

	private TextInput getNumberInEditModeInvoceTable() {
		return new TextInput(driver,
				By.xpath("//*[@class='tr-cell number editable-cell']"));
	}

	private TextInput getProductNameInEditModeInvoceTable() {
		return new TextInput(driver,
				By.xpath("//*[@class='tr-cell product editable-cell']/input"));
	}

	private TextInput getUnitsInEditModeInvoceTable() {
		return new TextInput(driver,
				By.xpath("//*[@class='tr-cell units editable-cell']"));
	}

	private TextInput getUnitsInEditModeInvoceTableOptions() {
		return new TextInput(driver,
				By.xpath("//*[@class='edit-input narrow-edit']/option"));
	}

	private DropDownList getUnitsInEditModeInvoceTableSelect() {
		return new DropDownList(driver,
				By.xpath("//*[@class='edit-input narrow-edit']"));
	}

	private TextInput getQuantityInEditModeInvoceTable() {
		return new TextInput(driver,
				By.xpath("//*[@class='tr-cell quantity editable-cell']/input"));
	}

	private TextInput getPriceInEditModeInvoceTable() {
		return new TextInput(driver,
				By.xpath("//*[@class='tr-cell price editable-cell']/input"));
	}

	private TextInput getTotalInEditModeInvoceTable() {
		return new TextInput(driver,
				By.xpath("//*[@class='tr-cell total editable-cell']"));
	}

// ---------------------- BUTTONS ------------------------------------

	// Check is buttons present on page

	public boolean isEditProductButtonPresents() {
		return getEditProductButton().isAvailable();
	}

	public boolean isDeleteProductButtonPresents() {
		return getDeleteProductButton().isAvailable();
	}

	public boolean isAcceptChangesButtonPresents() {
		return getAcceptChangesButton().isAvailable();
	}

	public boolean isCancelChangesButtonPresents() {
		return getCancelChangesButton().isAvailable();
	}

	// Click Buttons

	public void clickEditProductButton() {
		getEditProductButton().click();
	}

	public void clickDeleteProductButton() {
		getDeleteProductButton().click();
	}

	public void clickAcceptChangesButton() {
		getAcceptChangesButton().click();
	}

	public void clickCancelChangesButton() {
		getCancelChangesButton().click();
	}

	// Searching buttons in Invoice Table

	private Button getEditProductButton() {
		return new Button(
				driver,
				By.xpath(".//*[@class='action-button'][@src='images/pencil.png']"));
	}

	private Button getDeleteProductButton() {
		return new Button(
				driver,
				By.xpath(".//*[@class='action-button'][@src='images/trash.png']"));
	}

	public Button getAcceptChangesButton() {
		return new Button(
				driver,
				By.xpath(".//*[@class='action-button'][@src='images/accept.png']"));
	}

	private Button getCancelChangesButton() {
		return new Button(
				driver,
				By.xpath(".//*[@class='action-button'][@src='images/cancel.png']"));
	}
}
