package com.selenium.atf.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.selenium.atf.core.TestData.*;

import com.selenium.atf.core.BaseTest;
import com.selenium.atf.core.web.page.InvoicePage;

public class Test5 extends BaseTest {

	@BeforeMethod
	public void addingFirstRowToInvoiceTable() {
		InvoicePage currentPage = new InvoicePage(driver).LoadPage();
		currentPage.AddNewProduct(getProductName1(), getUnits1(),
				Integer.toString(getQuantity1()), Integer.toString(getPrice1()));
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());
	}

// ----------------- ADD NEW PRODUCT ----------------------------------

	// Test 1.2: Calculating Total price of products, two rows (positive test)
	@Test
	public void verifyCorrectingCalculatingTotalPrice() {
		InvoicePage currentPage = new InvoicePage(driver);
		currentPage.AddNewProduct(getProductName2(), "m\u00b2",
				Integer.toString(getQuantity2()), Integer.toString(getPrice2()));
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());

		// 1. Row with two new products should be appeared in Invoice Table
		assertThat("New Product has not added to Invoice Table",
				currentPage.getProductsTable().getRows().size(),
				equalTo(2));

		// 2. Text of added product should be the same as entered in input fields before
		// 3. Price of added product should be correct calculated in column "Total"

		// Product Number
		assertThat("Wrong Number in Row 1 in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
				.getNumberFieldTextFromInvoiceTable(),
				equalTo("1"));
		assertThat("Wrong Number in Row 2 in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("2")
				.getNumberFieldTextFromInvoiceTable(),
				equalTo("2"));
		
		// Product Name
		assertThat("Wrong Product Name in Row 1 in Invoice Table",
				getProductName1(), is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("1")
						.getProductNameFieldTextFromInvoiceTable())));
		assertThat("Wrong Product Name in Row 2 in Invoice Table",
				getProductName2(), is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("2")
						.getProductNameFieldTextFromInvoiceTable())));
		// Units
		assertThat("Wrong Unit in Row 1 in Invoice Table",
				getUnits1(),
				is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("1")
						.getUnitsFieldTextFromInvoiceTable())));
		assertThat("Wrong Units in Row 2 in Invoice Table",
				"m\u00b2",
				is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("2")
						.getUnitsFieldTextFromInvoiceTable())));
		// Quantity
		assertThat("Wrong Quantity in Row 1 in Invoice Table",
				Integer.toString(getQuantity1()), is(equalTo(currentPage
						.getProductsTable().getTableRowByNumber("1")
						.getQuantityFieldTextFromInvoiceTable())));
		assertThat("Wrong Quantity in Row 2 in Invoice Table",
				Integer.toString(getQuantity2()), is(equalTo(currentPage
						.getProductsTable().getTableRowByNumber("2")
						.getQuantityFieldTextFromInvoiceTable())));
		// Price
		assertThat("Wrong Price in Row 1 in Invoice Table",
				Integer.toString(getPrice1()), is(equalTo(currentPage
						.getProductsTable().getTableRowByNumber("1")
						.getPriceFieldTextFromInvoiceTable())));
		assertThat("Wrong Price in Row 2 in Invoice Table",
				Integer.toString(getPrice2()), is(equalTo(currentPage
						.getProductsTable().getTableRowByNumber("2")
						.getPriceFieldTextFromInvoiceTable())));
		// Total
		assertThat("Wrong Total in Row 1 in Invoice Table",
				Integer.toString(getTotal1()), is(equalTo(currentPage
						.getProductsTable().getTableRowByNumber("1")
						.getTotalFieldTextFromInvoiceTable())));
		assertThat("Wrong Total in Row 2 in Invoice Table",
				Integer.toString(getTotal2()), is(equalTo(currentPage
						.getProductsTable().getTableRowByNumber("2")
						.getTotalFieldTextFromInvoiceTable())));

		// 4.Total price (from all Total column) should be calculated correctly
		assertThat("Error result in field 'Total Price' in Invoice Table",
				currentPage.getTotalPriceFieldTextFromInvoiceTable(),
				is(equalTo(currentPage.getProductsTable().getTableTotalSum())));

		// 5. Buttons:
		//    - "Add Product" should be shown on Page
		assertThat("Error - Button 'Add Product' is not shown on the page",
				currentPage.isAddProductButtonPresents());
		//    - "Edit Product", "Delete Product" should be shown on Page
		assertThat("Error - Button 'Edit Product' is not shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("1")
						.isEditProductButtonPresents()) == true));
		assertThat("Error - Button 'Edit Product' is not shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("2")
						.isEditProductButtonPresents()) == true));
		assertThat("Error - Button 'Delete Product' is not shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("1")
						.isDeleteProductButtonPresents()) == true));
		assertThat("Error - Button 'Delete Product' is not shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("2")
						.isDeleteProductButtonPresents()) == true));
        //    - "Accept Changes" and "Cancel Changes" shouldn't be shown on Page
		assertThat("Error - Button 'Accept Changes' is shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("1")
						.isAcceptChangesButtonPresents()) == false));
		assertThat("Error - Button 'Accept Changes' is shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("2")
						.isAcceptChangesButtonPresents()) == false));
		assertThat("Error - Button 'Cancel Changes' is shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("1")
						.isAcceptChangesButtonPresents()) == false));
		assertThat("Error - Button 'Cancel Changes' is shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("2")
						.isAcceptChangesButtonPresents()) == false));

		// 6. Fields 'Main Product Name', 'Main Quantity' and 'Main Price' should be empty after adding new product
		assertThat("Field 'Main Product Name' contains wrong data",
				currentPage.getTextMainProductNameField(), is(equalTo("")));
		assertThat("Field 'Main Quantity' contains wrong data",
				currentPage.getTextMainQuantityField(), is(equalTo("")));
		assertThat("Field 'Main Price' contains wrong data",
				currentPage.getTextMainPriceField(), is(equalTo("")));

		// 7.  No any error classes should be presented in html in Main Fields
		assertThat("Error in Field 'Main Product Name'",
				currentPage.getMainProductNameFieldClass(),
				is(equalTo("input-field")));
		assertThat("Error in Field 'Main Quantity'",
				currentPage.getMainQuantityFieldClass(),
				is(equalTo("input-field")));
		assertThat("Error in Field 'Main Price'",
				currentPage.getMainPriceFieldClass(),
				is(equalTo("input-field")));
		
		// 8. No any edit/error classes should be presented in html in Invoice Table
		assertThat("Error in class of field 'Product Number' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
				.getTableProductNumberFieldClass(),
				is(equalTo("tr-cell number")));
		assertThat("Error in class of field 'Product Number' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("2")
				.getTableProductNumberFieldClass(),
				is(equalTo("tr-cell number")));
		
		assertThat("Error in class of field 'Product Name' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
				.getTableProductNameFieldClass(),
				is(equalTo("tr-cell product")));
		assertThat("Error in class of field 'Product Name' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("2")
				.getTableProductNameFieldClass(),
				is(equalTo("tr-cell product")));
		
		assertThat("Error in class of field 'Units' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
				.getTableUnitsFieldClass(),
				is(equalTo("tr-cell units")));
		assertThat("Error in class of field 'Units' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("2")
				.getTableUnitsFieldClass(),
				is(equalTo("tr-cell units")));
		
		assertThat("Error in class of field 'Quantity' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
				.getTableQuantityFieldClass(),
				is(equalTo("tr-cell quantity")));
		assertThat("Error in class of field 'Quantity' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("2")
				.getTableQuantityFieldClass(),
				is(equalTo("tr-cell quantity")));
		
		assertThat("Error in class of field 'Price' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
				.getTablePriceFieldClass(),
				is(equalTo("tr-cell price")));
		assertThat("Error in class of field 'Price' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("2")
				.getTablePriceFieldClass(),
				is(equalTo("tr-cell price")));
		
		assertThat("Error in class of field 'Total' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("1")
				.getTableTotalFieldClass(),
				is(equalTo("tr-cell total")));
		assertThat("Error in class of field 'Total' in Invoice Table",
				currentPage.getProductsTable().getTableRowByNumber("2")
				.getTableTotalFieldClass(),
				is(equalTo("tr-cell total")));
	}

	
// ------------------ EDIT ADDED PRODUCT ---------------------------------

	// Test 8.2: Checking error classes for Test8 (negative test)
	@Test
	public void VerifyManyAcceptChangesNegativeChekingErrors() {
		InvoicePage currentPage = new InvoicePage(driver);
		currentPage.getProductsTable().getTableRowByNumber("1")
				.clickEditProductButton();
		currentPage.waitUntilAvailable();

		// Case1: (Field Product Name in Invoice Table is empty)
		currentPage
				.getProductsTable()
				.getTableEditRowByNumber("1")
				.EditAndAcceptAddedProduct("", "pcs",
						Integer.toString(getQuantity2()), Integer.toString(getPrice2()));
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());
		
		// 8. Error classes should be presented in html of page.
		assertThat("Error in Field 'Product Name' in Invoice Table",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTableProductNameFieldEditClass(),
				is(equalTo("edit-input wide-edit error")));

		// Case2: (Field Quantity in Invoice Table is empty)
		currentPage
				.getProductsTable()
				.getTableEditRowByNumber("1")
				.EditAndAcceptAddedProduct(getProductName2(), "m", "",
						Integer.toString(getPrice2()));
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());
		
		// 8. Error classes should be presented in html of page.
		assertThat("Error in Field 'Quantity' in Invoice Table",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTableQuantityFieldEditClass(),
				is(equalTo("edit-input narrow-edit error")));

		// Case3: (Field Price in Invoice Table is empty)
		currentPage
				.getProductsTable()
				.getTableEditRowByNumber("1")
				.EditAndAcceptAddedProduct(getProductName2(), "l",
						Integer.toString(getQuantity2()), "");
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());
		
		// 8. Error classes should be presented in html of page.
		assertThat("Error in Field 'Price' in Invoice Table",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTablePriceFieldEditClass(),
				is(equalTo("edit-input narrow-edit error")));

		// Case4: (All fields in Invoice Table are empty)
		currentPage.getProductsTable()
				.getTableEditRowByNumber("1")
				.EditAndAcceptAddedProduct("", "m\u00b2", "", "");
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());
		
		// 8. Error classes should be presented in html of page.
		assertThat("Error in Field 'Product Name' in Invoice Table",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTableProductNameFieldEditClass(),
				is(equalTo("edit-input wide-edit error")));
		assertThat("Error in Field 'Quantity' in Invoice Table",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTableQuantityFieldEditClass(),
				is(equalTo("edit-input narrow-edit error")));
		assertThat("Error in Field 'Price' in Invoice Table",
				currentPage.getProductsTable()
						.getTableEditRowByNumber("1")
						.getTablePriceFieldEditClass(),
				is(equalTo("edit-input narrow-edit error")));
	}


// ---------------- DELETE ADDED PRODUCT ---------------------------------------

	// Test 11: Exist one product, delete it - no one product should be presented in Invoice Table after deleting Row 
	@Test
	public void verifyDeleteProductClick() {
		InvoicePage currentPage = new InvoicePage(driver);
		currentPage.getProductsTable().getTableRowByNumber("1")
				.clickDeleteProductButton();
		currentPage.waitUntilAvailable();

		// 1. No any Row should be presented in Invoice Table
		assertThat("Row with product is not deleted from Invoice Table",
				currentPage.getProductsTable().getRows().size(),
				equalTo(0));

		// 2. Total Price (from all rows in Total column) should be recalculated to "0"
		assertThat("Error result in field 'Total Price' in Invoice Table",
				currentPage.getTotalPriceFieldTextFromInvoiceTable(),
				is(equalTo("0")));

		// 3. Input fields (Main) should be empty
		// 4. No any error classes should be presented in html in Main Fields
		assertThat("Field 'Main Product Name' contains wrong data",
				currentPage.getTextMainProductNameField(), is(equalTo("")));
		assertThat("Field 'Main Quantity' contains wrong data",
				currentPage.getTextMainQuantityField(), is(equalTo("")));
		assertThat("Field 'Main Price' contains wrong data",
				currentPage.getTextMainPriceField(), is(equalTo("")));
	}

	// Test 12: Exist two row with products in Invoice Table, delete first row
	@Test
	public void verifyDeletingOneProductFromTwo() {
		InvoicePage currentPage = new InvoicePage(driver);
		currentPage.AddNewProduct(getProductName2(), getUnits2(),
				Integer.toString(getQuantity2()), Integer.toString(getPrice2()));
		currentPage.waitUntilAvailable();
		assertThat("Some of Main fields is not exist/or have error",
				currentPage.isAvailable());
		currentPage.waitUntilAvailable();
		currentPage.getProductsTable().getTableRowByNumber("1")
				.clickDeleteProductButton();

		// 1. Only one Row should be left in Invoice Table,
		assertThat("More than 1 Row with Product presents in Invoice Table",
				currentPage.getProductsTable().getRows().size(),
				equalTo(1));

		// 2. Text of all fields in first row should be the same as text in second row before
		// 3. Price of field "total" should be correct calculated in column
		assertThat("Wrong Number in Invoice Table",
				getProductNumber1(),
				is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("1")
						.getNumberFieldTextFromInvoiceTable())));
		assertThat("Wrong Product Name in Invoice Table",
				getProductName2(),
				is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("1")
						.getProductNameFieldTextFromInvoiceTable())));
		assertThat("Wrong Units in Invoice Table",
				getUnits2(),
				is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("1")
						.getUnitsFieldTextFromInvoiceTable())));
		assertThat("Wrong Quantity in Invoice Table",
				Integer.toString(getQuantity2()), is(equalTo(currentPage
						.getProductsTable().getTableRowByNumber("1")
						.getQuantityFieldTextFromInvoiceTable())));
		assertThat("Wrong Price in Invoice Table",
				Integer.toString(getPrice2()),
				is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("1")
						.getPriceFieldTextFromInvoiceTable())));
		assertThat("Wrong Total in Invoice Table",
				Integer.toString(getTotal2()),
				is(equalTo(currentPage.getProductsTable()
						.getTableRowByNumber("1")
						.getTotalFieldTextFromInvoiceTable())));

		// 4. Total price (from all rows in Total column) should be recalculated correctly
		assertThat("Error result in field 'Total Price' in Invoice Table",
				currentPage.getTotalPriceFieldTextFromInvoiceTable(),
				is(equalTo(Integer.toString(getTotal2()))));

		// 5. Buttons:
		//    - "Add Product" should be shown on Page
		assertThat("Error - Button 'Add Product' is not shown on the page",
				currentPage.isAddProductButtonPresents());
		//    - "Edit Product", "Delete Product" should be shown on Page
		assertThat("Error - Button 'Edit Product' is not shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("1")
						.isEditProductButtonPresents()) == true));
		assertThat("Error - Button 'Delete Product' is not shown in Invoice Table",
				((currentPage.getProductsTable().getTableRowByNumber("1")
						.isDeleteProductButtonPresents()) == true));
        //    - "Accept Changes" and "Cancel Changes" shouldn't be shown on Page
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
