package com.selenium.atf.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.testng.annotations.Test;

import com.selenium.atf.core.BaseTest;
import com.selenium.atf.core.CsvDataProvider;
import com.selenium.atf.core.web.page.InvoicePage;

public class Test2 extends BaseTest {

// ----------------- ADD NEW PRODUCT ----------------------------------

	// Test 2.1: New incorrect product, product isn't added to Invoice Table, one item (negative test)
	@Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class)
	public void verifyIncorrectingAddingProduct(Map<String, String> testData) {
		InvoicePage currentPage = new InvoicePage(driver).LoadPage()
				.AddNewProduct(testData.get("product_name"),
						testData.get("units"), testData.get("quantity"),
						testData.get("price"));
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());

		// 1. No any Row should be presented in Invoice Table
		assertThat("Error - Some new Row added to Invoice Table",
				currentPage.getProductsTable().getRows().size(),
				equalTo(0));
		
		// 2. Total Price (from all rows in Total column) should be recalculated to "0"
		assertThat("Error result in field 'Total Price' in Invoice Table",
				currentPage.getTotalPriceFieldTextFromInvoiceTable(),
				is(equalTo("0")));

		// 3. Main Input fields should remember text that was entered to them before
		assertThat("Field 'Main Product Name' contains wrong data",
				currentPage.getTextMainProductNameField(),
				is(equalTo(testData.get("product_name"))));
		assertThat("Field 'Main Quantity' contains wrong data",
				currentPage.getTextMainQuantityField(),
				is(equalTo(testData.get("quantity"))));
		assertThat("Field 'Main Price' contains wrong data",
				currentPage.getTextMainPriceField(),
				is(equalTo(testData.get("price")))); 
	}

	
	// Test 2.2: Checking error classes for Test2 (negative test)
	@Test
	public void verifyIncorrectingAddingProductChekingErrors() {
		InvoicePage currentPage = new InvoicePage(driver);

		// Case1: (Field Main Product Name empty)
		currentPage.LoadPage().AddNewProduct("", "pcs", "10", "12");
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());
		// 5. Error classes should be presented in html of page.
		assertThat("Error in Field 'Main Product Name'",
				currentPage.getMainProductNameFieldClass(),
				is(equalTo("input-field error")));

		// Case2: (Field Main Quantity empty)
		currentPage.LoadPage().AddNewProduct("Notebook Dell 5151", "pcs", "",
				"12");
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());
		// 5. Error classes should be presented in html of page.
		assertThat("Error in Field 'Main Quantity'",
				currentPage.getMainQuantityFieldClass(),
				is(equalTo("input-field error")));

		// Case3: (Field Main Price empty)
		currentPage.LoadPage().AddNewProduct("Notebook Dell 5151", "pcs", "10",
				"");
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());
		// 5. Error classes should be presented in html of page.
		assertThat("Error in Field 'Main Price'",
				currentPage.getMainPriceFieldClass(),
				is(equalTo("input-field error")));

		// Case4: (All Main fields empty)
		currentPage.LoadPage().AddNewProduct("", "pcs", "", "");
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());
		// 5. Error classes should be presented in html of page.
		assertThat("Error in Field 'Main Product Name'",
				currentPage.getMainProductNameFieldClass(),
				is(equalTo("input-field error")));
		assertThat("Error in Field 'Main Quantity'",
				currentPage.getMainQuantityFieldClass(),
				is(equalTo("input-field error")));
		assertThat("Error in Field 'Main Price'",
				currentPage.getMainPriceFieldClass(),
				is(equalTo("input-field error")));
	}
}
