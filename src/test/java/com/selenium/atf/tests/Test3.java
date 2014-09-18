package com.selenium.atf.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.selenium.atf.core.TestData.*;
import com.selenium.atf.core.BaseTest;
import com.selenium.atf.core.web.page.InvoicePage;

public class Test3 extends BaseTest {

	@BeforeMethod
	public void addingFirstRowToInvoiceTable() {
		InvoicePage currentPage = new InvoicePage(driver).LoadPage();
		currentPage.AddNewProduct(getProductName1(), getUnits1(),
				Integer.toString(getQuantity1()), Integer.toString(getPrice1()));
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());
	}
	

	// Test 4: No any changes in edit fields in Invoice Table -> click button "Accept Changes"
	@Test
	public void verifyAcceptChangesButtonClick() {
		InvoicePage currentPage = new InvoicePage(driver);
		currentPage.getProductsTable().getTableRowByNumber("1")
				.clickEditProductButton();
		currentPage.waitUntilAvailable();
		currentPage.getProductsTable()
				.getTableEditRowByNumber("1")
				.clickAcceptChangesButton();
	}
	

	// Test 5: No any changes in edit fields in Invoice Table -> click button "Cancel Changes"
	@Test
	public void verifyCancelChangesButtonClick() {
		InvoicePage currentPage = new InvoicePage(driver);
		currentPage.getProductsTable().getTableRowByNumber("1")
				.clickEditProductButton();
		currentPage.waitUntilAvailable();
		currentPage.getProductsTable()
				.getTableEditRowByNumber("1")
				.clickCancelChangesButton();
	}
	

	// Test 7: Changing dates to correct in edit fields in Invoice Table -> click button "Cancel Changes" (positive test)
	@Test
	public void verifyEditingCancelChangespositive() {
		InvoicePage currentPage = new InvoicePage(driver);
		currentPage.getProductsTable().getTableRowByNumber("1")
				.clickEditProductButton();
		currentPage.waitUntilAvailable();
		currentPage
				.getProductsTable()
				.getTableEditRowByNumber("1")
				.EditAndCancelAddedProduct(getProductName2(), getUnits2(),
						Integer.toString(getQuantity2()), Integer.toString(getPrice2()));
		currentPage.waitUntilAvailable();
	}
	

	// Test 9: Changing dates to incorrect in edit fields in Invoice Table -> click button "Cancel Changes" (negative test)
	@Test
	public void verifyEditingCancelChangesNegative() {
		InvoicePage currentPage = new InvoicePage(driver);
		currentPage.getProductsTable().getTableRowByNumber("1")
				.clickEditProductButton();
		currentPage.waitUntilAvailable();
		currentPage.getProductsTable()
				.getTableEditRowByNumber("1")
				.EditAndCancelAddedProduct("", "kg", "", "");
		currentPage.waitUntilAvailable();
	}

	
	// Test 10: Changing dates to incorrect in edit fields in Invoice Table -> click "Accept Changes" -> click button "Cancel Changes" (negative test)
	@Test
	public void verifyEditingAccept_CancelChangesNegative() {
		InvoicePage currentPage = new InvoicePage(driver);
		currentPage.getProductsTable().getTableRowByNumber("1")
				.clickEditProductButton();
		currentPage.waitUntilAvailable();
		currentPage.getProductsTable()
				.getTableEditRowByNumber("1")
				.EditAndAcceptAddedProduct("", "kg", "", "");
		currentPage.waitUntilAvailable();
		currentPage.getProductsTable()
				.getTableEditRowByNumber("1")
				.EditAndCancelAddedProduct("", "kg", "", "");
		currentPage.waitUntilAvailable();
	} 
	

	@AfterMethod
	public void chekingSomeAsserts() {
		InvoicePage currentPage = new InvoicePage(driver);
		// 1. No any New Row should be added to Invoice Table
		assertThat("Error - Some New Row added to Invoice Table",
				currentPage.getProductsTable().getRows().size(),
				equalTo(1));

		// 2. Text in Row with edited product shouldn't be changed in Invoice Table
		// 3. Price of edited product shouldn't be changed in Invoice Table,
		assertThat("Wrong Number in Invoice Table",
				getProductNumber1(),
				is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("1")
						.getNumberFieldTextFromInvoiceTable())));
		assertThat("Wrong Product Name in Invoice Table",
				getProductName1(),
				is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("1")
						.getProductNameFieldTextFromInvoiceTable())));
		assertThat("Wrong Units in Invoice Table",
				getUnits1(),
				is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("1")
						.getUnitsFieldTextFromInvoiceTable())));
		assertThat("Wrong Quantity in Invoice Table",
				Integer.toString(getQuantity1()), is(equalTo(currentPage
						.getProductsTable().getTableRowByNumber("1")
						.getQuantityFieldTextFromInvoiceTable())));
		assertThat("Wrong Price in Invoice Table",
				Integer.toString(getPrice1()),
				is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("1")
						.getPriceFieldTextFromInvoiceTable())));
		assertThat("Wrong Total in Invoice Table",
				Integer.toString(getTotal1()),
				is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("1")
						.getTotalFieldTextFromInvoiceTable())));

		// 4. Total price (from all rows in Total column) should be calculated correctly
		assertThat("Error result in field 'Total Price' in Invoice Table",
				currentPage.getTotalPriceFieldTextFromInvoiceTable(),
				is(equalTo(Integer.toString(getTotal1()))));

		// 5. Buttons:
		// - "Add Product" should be shown on Page
		assertThat("Error - Button 'Add Product' is not shown on the page",
				currentPage.isAddProductButtonPresents());
		// - "Edit Product", "Delete Product" should be shown on Page
		assertThat("Error - Button 'Edit Product' is not shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("1")
						.isEditProductButtonPresents()) == true));
		assertThat("Error - Button 'Delete Product' is not shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("1")
						.isDeleteProductButtonPresents()) == true));
		// - "Accept Changes" and "Cancel Changes" shouldn't be shown on Page
		assertThat("Error - Button 'Accept Changes' is shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("1")
						.isAcceptChangesButtonPresents()) == false));
		assertThat("Error - Button 'Cancel Changes' is shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("1")
						.isAcceptChangesButtonPresents()) == false));

		// 6. Fields 'Main Product Name', 'Main Quantity' and 'Main Price' should be empty after adding new product
		assertThat("Field 'Main Product Name' contains wrong data",
				currentPage.getTextMainProductNameField(), is(equalTo("")));
		assertThat("Field 'Main Quantity' contains wrong data",
				currentPage.getTextMainQuantityField(), is(equalTo("")));
		assertThat("Field 'Main Price' contains wrong data",
				currentPage.getTextMainPriceField(), is(equalTo("")));

		// 7. No any error classes should be presented in html in Main Fields
		assertThat("Error in Field 'Main Product Name'",
				currentPage.getMainProductNameFieldClass(),
				is(equalTo("input-field")));
		assertThat("Error in Field 'Main Quantity'",
				currentPage.getMainQuantityFieldClass(),
				is(equalTo("input-field")));
		assertThat("Error in Field 'Main Price'",
				currentPage.getMainPriceFieldClass(),
				is(equalTo("input-field")));

		// 8. No any edit classes should be presented in html in Invoice Table
		assertThat("Error in class of field 'Product Number' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
						.getTableProductNumberFieldClass(),
				is(equalTo("tr-cell number")));
		assertThat("Error in class of field 'Product Name' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
						.getTableProductNameFieldClass(),
				is(equalTo("tr-cell product")));
		assertThat("Error in class of field 'Units' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
						.getTableUnitsFieldClass(),
				is(equalTo("tr-cell units")));
		assertThat("Error in class of field 'Quantity' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
						.getTableQuantityFieldClass(),
				is(equalTo("tr-cell quantity")));
		assertThat("Error in class of field 'Price' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
						.getTablePriceFieldClass(),
				is(equalTo("tr-cell price")));
		assertThat("Error in class of field 'Total' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
						.getTableTotalFieldClass(),
				is(equalTo("tr-cell total")));
	}
}