package com.selenium.atf.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.selenium.atf.core.BaseTest;
import com.selenium.atf.core.CsvDataProvider;
import static com.selenium.atf.core.TestData.*;
import com.selenium.atf.core.web.page.InvoicePage;

public class Test1 extends BaseTest {
	
// ----------------- ADD NEW PRODUCT ----------------------------------

	
		// Test 1.1: New product added correctly, one row (positive test)
		@Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class)
		public void verifyCorrectingAddingProduct(Map<String, String> testData) {
			InvoicePage currentPage = new InvoicePage(driver).LoadPage()
					.AddNewProduct(testData.get("product_name"),
							testData.get("units"), testData.get("quantity"),
							testData.get("price"));
			currentPage.waitUntilAvailable();
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
			}
			assertThat("Some of Main fields is not exist/or have error",
					currentPage.isAvailable());

			// 1. Row with new product should be added to Invoice Table
			assertThat("New Product has not added to Invoice Table",
					"1",
					is(equalTo(Integer.toString(currentPage.getProductsTable()
							.getRows().size()))));

			// 2. Text of added product should be the same as entered in input fields before
			// 3. Price of added product should be calculated correctly in column "Total"
			assertThat("Wrong Number in Invoice Table", "1", is(equalTo(currentPage
					.getProductsTable().getTableRowById("table-row")
					.getNumberFieldTextFromInvoiceTable())));
			assertThat("Wrong Product Name in Invoice Table",
					testData.get("product_name"), is(equalTo(currentPage
							.getProductsTable().getTableRowById("table-row")
							.getProductNameFieldTextFromInvoiceTable())));
			assertThat("Wrong Units in Invoice Table",
					testData.get("units"),
					is(equalTo(currentPage.getProductsTable()
							.getTableRowById("table-row")
							.getUnitsFieldTextFromInvoiceTable())));
			assertThat("Wrong Quantity in Invoice Table",
					testData.get("quantity"),
					is(equalTo(currentPage.getProductsTable()
							.getTableRowById("table-row")
							.getQuantityFieldTextFromInvoiceTable())));
			assertThat("Wrong Price in Invoice Table",
					testData.get("price"),
					is(equalTo(currentPage.getProductsTable()
							.getTableRowById("table-row")
							.getPriceFieldTextFromInvoiceTable())));
			assertThat("Wrong Total in Invoice Table",
					testData.get("total"),
					is(equalTo(currentPage.getProductsTable()
							.getTableRowById("table-row")
							.getTotalFieldTextFromInvoiceTable())));

			// 4. Total price (from all rows in Total column) should be calculated correctly
			assertThat("Error result in field 'Total Price' in Invoice Table",
					currentPage.getTotalPriceFieldTextFromInvoiceTable(),
					is(equalTo(testData.get("total"))));
		}

		
// ------------------ EDIT ADDED PRODUCT ---------------------------------
		
		// Test 6: Changing dates to correct in edit fields in Invoice Table -> click button "Accept Changes" (positive test)
		@Test (dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class)
		public void verifyManyAcceptChangesPositive(Map<String, String> testData) {
			InvoicePage currentPage = new InvoicePage(driver).LoadPage();
			currentPage.AddNewProduct(getProductName1(), getUnits1(),
					Integer.toString(getQuantity1()), Integer.toString(getPrice1()));
			currentPage.waitUntilAvailable();
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
			}
			assertThat("Some of Main fields is not exist/or have error",
					currentPage.isAvailable());
			
			currentPage.getProductsTable().getTableRowById("table-row")
					.clickEditProductButton();
			currentPage.waitUntilAvailable();
			// try{Thread.sleep(2000);} catch(Exception e){}
			currentPage
					.getProductsTable()
					.getTableEditRowById("table-row editable-row")
					.EditAndAcceptAddedProduct(testData.get("product_name"),
							testData.get("units"), testData.get("quantity"),
							testData.get("price"));
			currentPage.waitUntilAvailable();
			// try{Thread.sleep(2000);} catch(Exception e){}

			// 1. No any new Row should be added to Invoice Table
			assertThat("Error - Some new Row added to Invoice Table",
					"1",
					is(equalTo(Integer.toString(currentPage.getProductsTable()
							.getRows().size()))));
			
			// 2. Text in Row with edited product shouldn't be changed in Invoice Table
			// 3. Price of edited product shouldn't be changed" in Invoice Table
			assertThat("Wrong Number in Invoice Table",
					getProductNumber1(),
					is(equalTo(currentPage.getProductsTable()
							.getTableRowById("table-row")
							.getNumberFieldTextFromInvoiceTable())));
			assertThat("Wrong Product Name in Invoice Table",
					testData.get("product_name"), is(equalTo(currentPage
							.getProductsTable().getTableRowById("table-row")
							.getProductNameFieldTextFromInvoiceTable())));
			assertThat("Wrong Units in Invoice Table",
					testData.get("units"),
					is(equalTo(currentPage.getProductsTable()
							.getTableRowById("table-row")
							.getUnitsFieldTextFromInvoiceTable())));
			assertThat("Wrong Quantity in Invoice Table",
					testData.get("quantity"),
					is(equalTo(currentPage.getProductsTable()
							.getTableRowById("table-row")
							.getQuantityFieldTextFromInvoiceTable())));
			assertThat("Wrong Price in Invoice Table",
					testData.get("price"),
					is(equalTo(currentPage.getProductsTable()
							.getTableRowById("table-row")
							.getPriceFieldTextFromInvoiceTable())));
			assertThat("Wrong Total in Invoice Table",
					testData.get("total"),
					is(equalTo(currentPage.getProductsTable()
							.getTableRowById("table-row")
							.getTotalFieldTextFromInvoiceTable())));
			
			// 4. Total price (from all rows in Total column) should be calculated correctly
			assertThat("Error result in field 'Total Price' in Invoice Table",
					currentPage.getTotalPriceFieldTextFromInvoiceTable(),
					is(equalTo(testData.get("total"))));
		}
		
		@AfterMethod
		public void chekingSomeAsserts() {
			InvoicePage currentPage = new InvoicePage(driver);
			// 5. Buttons:
			//    - "Add Product" should be shown on Page
			assertThat("Error - Button 'Add Product' is not shown on the page",
					currentPage.isAddProductButtonPresents());
			//    - "Edit Product", "Delete Product" should be shown on Page
			assertThat("Error - Button 'Edit Product' is not shown in Invoice Table",
					((currentPage.getProductsTable().getTableRowById("table-row")
							.isEditProductButtonPresents()) == true));
			assertThat("Error - Button 'Delete Product' is not shown in Invoice Table",
					((currentPage.getProductsTable().getTableRowById("table-row")
							.isDeleteProductButtonPresents()) == true));
	        //    - "Accept Changes" and "Cancel Changes" shouldn't be shown on Page
			assertThat("Error - Button 'Accept Changes' is shown in Invoice Table",
					((currentPage.getProductsTable().getTableRowById("table-row")
							.isAcceptChangesButtonPresents()) == false));
			assertThat("Error - Button 'Cancel Changes' is shown in Invoice Table",
					((currentPage.getProductsTable().getTableRowById("table-row")
							.isAcceptChangesButtonPresents()) == false));

			// 6. Fields 'Main Product Name', 'Main Quantity' and 'Main Price' should be empty after adding new product
			assertThat("Field 'Main Product Name' contains wrong data",
					currentPage.getTextMainProductNameField(), is(equalTo("")));
			assertThat("Field 'Main Quantity' contains wrong data",
					currentPage.getTextMainQuantityField(), is(equalTo("")));
			assertThat("Field 'Main Price' contains wrong data",
					currentPage.getTextMainPriceField(), is(equalTo("")));

			// 7. No any error classes should be presented in html in Main Fields
			assertThat("Error in class of field 'Main Product Name'",
					currentPage.getMainProductNameFieldClass(),
					is(equalTo("input-field")));
			assertThat("Error in class of field 'Main Quantity'",
					currentPage.getMainQuantityFieldClass(),
					is(equalTo("input-field")));
			assertThat("Error in class of field 'Main Price'",
					currentPage.getMainPriceFieldClass(),
					is(equalTo("input-field")));
			
			// 8. No any edit classes should be presented in html in Invoice Table 
			assertThat("Error in class of field 'Product Number' in Invoice Table",
					currentPage.getProductsTable().getTableRowById("table-row")
					.getTableProductNumberFieldClass(),
					is(equalTo("tr-cell number")));
			assertThat("Error in class of field 'Product Name' in Invoice Table",
					currentPage.getProductsTable().getTableRowById("table-row")
					.getTableProductNameFieldClass(),
					is(equalTo("tr-cell product")));
			assertThat("Error in class of field 'Units' in Invoice Table",
					currentPage.getProductsTable().getTableRowById("table-row")
					.getTableUnitsFieldClass(),
					is(equalTo("tr-cell units")));
			assertThat("Error in class of field 'Quantity' in Invoice Table",
					currentPage.getProductsTable().getTableRowById("table-row")
					.getTableQuantityFieldClass(),
					is(equalTo("tr-cell quantity")));
			assertThat("Error in class of field 'Price' in Invoice Table",
					currentPage.getProductsTable().getTableRowById("table-row")
					.getTablePriceFieldClass(),
					is(equalTo("tr-cell price")));
			assertThat("Error in class of field 'Total' in Invoice Table",
					currentPage.getProductsTable().getTableRowById("table-row")
					.getTableTotalFieldClass(),
					is(equalTo("tr-cell total")));
		}
}
