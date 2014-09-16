package com.selenium.atf.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.selenium.atf.core.BaseTest;

import static com.selenium.atf.core.TestData.*;

import com.selenium.atf.core.CsvDataProvider;
import com.selenium.atf.core.web.page.InvoicePage;

public class Test4 extends BaseTest {

	 @BeforeMethod
	public void addingFirstRowToInvoiceTable() {
		InvoicePage currentPage = new InvoicePage(driver).LoadPage();
		currentPage.AddNewProduct(getProductName1(), getUnits1(),
				Integer.toString(getQuantity1()), Integer.toString(getPrice1()));
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());
	}
	
// ------------------ EDIT ADDED PRODUCT ---------------------------------

	// Test 3: Edit mode should be enabled after clicking button "Edit product" (positive test)
	@Test
	public void verifyIsEditModeEnabled() {
		InvoicePage currentPage = new InvoicePage(driver);
		currentPage.getProductsTable().getTableRowByNumber("1")
				.clickEditProductButton();
		currentPage.waitUntilAvailable();

		// 1. No any new row should be appeared in Invoice Table
		assertThat("Error - Some New Row added to Invoice Table",
				currentPage.getProductsTable().getRows().size(),
				is(equalTo(0)));
		
		// 2. Text in row in Edit mode should be the same like without Edit mode in Invoice Table
		// 3. Total of product shouldn't be changed in Invoice Table
		assertThat("Field 'Number' contains wrong data",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTextTableNumberFieldInEdit(),
				is(equalTo(getProductNumber1())));
		assertThat("Field 'Product Name' contains wrong data",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTextTableProductNameFieldInEdit(),
				is(equalTo(getProductName1())));
		assertThat("Field 'Units' contains wrong data",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTextTableUnitsFieldInEdit(), is(equalTo(getUnits1())));
		assertThat("Field 'Quantity' contains wrong data",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTextTableQuantityFieldInEdit(),
				is(equalTo(Integer.toString(getQuantity1()))));
		assertThat("Field 'Price' contains wrong data",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTextTablePriceFieldInEdit(),
				is(equalTo(Integer.toString(getPrice1()))));
		assertThat("Field 'Total' contains wrong data",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTextTableTotalFieldInEdit(),
				is(equalTo(Integer.toString(getTotal1()))));
		
		// 8. Edit classes should be presented in html in Invoice Table
		assertThat("Field 'Product Name' is not in edit mode in Invoice Table",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTableProductNameFieldEditClass(),
				is(equalTo("edit-input wide-edit")));
		assertThat("Field 'Units' is not in edit mode in Invoice Table",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTableUnitsFieldEditClass(),
				is(equalTo("tr-cell units editable-cell")));
		assertThat("Field 'Quantity' is not in edit mode in Invoice Table",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTableQuantityFieldEditClass(),
				is(equalTo("edit-input narrow-edit")));
		assertThat("Field 'Price' is not in edit mode in Invoice Table",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTablePriceFieldEditClass(),
				is(equalTo("edit-input narrow-edit")));
		assertThat("Field 'Total' is not in edit mode in Invoice Table",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTableTotalFieldEditClass(),
				is(equalTo("tr-cell total editable-cell")));
	}
	
	
	// Test 8.1: Changing dates to incorrect in edit fields in Invoice Table -> click button "Accept Changes" (negative test)
	@Test (dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class)
	public void verifyManyAcceptChangesNegative(Map<String, String> testData) {
		InvoicePage currentPage = new InvoicePage(driver);
		currentPage.getProductsTable().getTableRowByNumber("1")
				.clickEditProductButton();
		currentPage.waitUntilAvailable();
		currentPage
				.getProductsTable()
				.getTableEditRowByNumber("1")
				.EditAndAcceptAddedProduct(testData.get("product_name"),
						testData.get("units"), testData.get("quantity"),
						testData.get("price"));
		currentPage.waitUntilAvailable();
		
		// 1. No any new Row should be added to Invoice Table
		assertThat("Error - Some new Row added to Invoice Table",
				currentPage.getProductsTable().getRows().size(),
				equalTo(0));
		
		// 2. Text in Row with edited product should be remembered after error in Invoice Table
		// 3. Total of product shouldn't be changed in Invoice Table
		assertThat("Field 'Number' contains wrong data",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTextTableNumberFieldInEdit(), is(equalTo("1")));
		assertThat("Field 'Product Name' contains wrong data",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTextTableProductNameFieldInEdit(),
				is(equalTo(testData.get("product_name"))));
		assertThat("Field 'Units' contains wrong data",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTextTableUnitsFieldInEdit(), is(equalTo("pcs")));
		assertThat("Field 'Quantity' contains wrong data",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTextTableQuantityFieldInEdit(),
				is(equalTo(testData.get("quantity"))));
		assertThat("Field 'Price' contains wrong data",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTextTablePriceFieldInEdit(),
				is(equalTo(testData.get("price"))));
		assertThat("Field 'Total' contains wrong data",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTextTableTotalFieldInEdit(),
				is(equalTo(Integer.toString(getTotal1()))));
	}
	
	
	@AfterMethod
	public void chekingSomeAsserts() {
		InvoicePage currentPage = new InvoicePage(driver);

		// 4. Total Price of Invoice Table shouldn't be changed,
		assertThat("Error result in field 'Total Price' in Invoice Table",
				currentPage.getTotalPriceFieldTextFromInvoiceTable(),
				is(equalTo(Integer.toString(getTotal1()))));

		// 5. Buttons:
		//    - "Add Product" should be shown on Page
		assertThat("Error - Button 'Add Product' is not shown on the page",
				currentPage.isAddProductButtonPresents());
        //    - "Accept Changes" and "Cancel Changes" should be shown on Page
		assertThat("Error - Button 'Accept Changes' is shown in Invoice Table",
				((currentPage.getProductsTable().getTableEditRowByNumber("1")
						.isAcceptChangesButtonPresents()) == true));
		assertThat("Error - Button 'Cancel Changes' is shown in Invoice Table",
				((currentPage.getProductsTable().getTableEditRowByNumber("1")
						.isAcceptChangesButtonPresents()) == true));
		//    - "Edit Product", "Delete Product" shouldn't be shown on Page
		assertThat("Error - Button 'Edit Product' is not shown in Invoice Table",
				((currentPage.getProductsTable().getTableEditRowByNumber("1")
						.isEditProductButtonPresents()) == false));
		assertThat("Error - Button 'Delete Product' is not shown in Invoice Table",
				((currentPage.getProductsTable().getTableEditRowByNumber("1")
						.isDeleteProductButtonPresents()) == false));

		// 6. Input fields (Main) should be empty after clicking button "Edit product"
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
	}
}
