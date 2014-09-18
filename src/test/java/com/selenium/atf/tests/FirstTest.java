package com.selenium.atf.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Map;

import org.testng.annotations.Test;

import com.selenium.atf.core.BaseTest;
import com.selenium.atf.core.CsvDataProvider;
import com.selenium.atf.core.web.page.InvoicePage;


public class FirstTest extends BaseTest {
	
//-------------------------------------- ADD NEW PRODUCT ----------------------------------------------------------------------------	
  
// Test 1.1: New product added correctly, one item (positive test)
	
		    @Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class)
	    public void verifyCorrectingAddingProduct(Map<String, String> testData) {
		InvoicePage currentPage = new InvoicePage(driver).LoadPage().AddNewProduct(testData.get("product_name"), testData.get("units"), testData.get("quantity"), testData.get("price"));
		try{Thread.sleep(2000);} catch(Exception e){}

		    assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable());
		//  1. Row with new product should be added in Invoice Table,
		//  2. Text of added product should be the same as entered in input fields before,
		//  3. Price of added product should be correct calculated in column "Total", 
		//  7. No any edit/error classes should be presented in html in Invoice Table, 	    
    
            assertThat("Wrong Product Name in Invoice Table", testData.get("product_name"), is(equalTo(currentPage.getProductsTable().getTableRowById("table-row").getProductNameFromInvoiceTable())));
	        
		    assertThat("Wrong Number in Invoice Table", "1", is(equalTo(currentPage.isTextInNumberFieldIsCorrect())));
		    assertThat("Wrong Product Name in Invoice Table", testData.get("product_name"), is(equalTo(currentPage.isTextInProductNameFieldIsCorrect())));
			assertThat("Wrong Units in Invoice Table", testData.get("units"), is(equalTo(currentPage.isTextInUnitsFieldIsCorrect())));
			assertThat("Wrong Quantity in Invoice Table", testData.get("quantity"), is(equalTo(currentPage.isTextInQuantityFieldIsCorrect())));
			assertThat("Wrong Price in Invoice Table", testData.get("price"), is(equalTo(currentPage.isTextInPriceFieldIsCorrect())));
			assertThat("Wrong Total in Invoice Table", testData.get("total"), is(equalTo(currentPage.isTextTotalFieldIsCorrect())));
		
	    //  4. Total price (from all rows in Total column) should be calculated correctly
			assertThat("Error result in field 'Total Price' in Invoice Table", currentPage.isTextTotalPriceFieldIsCorrect(), is(equalTo(testData.get("total")))); 
			   
		//  5. Buttons "Add Product", "Edit Product", "Delete Product" should be present on Page,
			assertThat("Button 'Add Product' absent on the page", currentPage.isAddProductButtonPresents());
			assertThat("Button 'Edit Product' absent on the page", currentPage.isEditProductButtonPresents());
			assertThat("Button 'Delete Product' absent on the page", currentPage.isDeleteProductButtonPresents());
			
		//  6. Fields 'Main Product Name', 'Main Quantity' and 'Main Price' should be empty after adding new product 
		    assertThat("Field 'Main Product Name' contains wrong data", currentPage.getTextMainProductNameField(), is(equalTo("")));
		    assertThat("Field 'Main Quantity' contains wrong data", currentPage.getTextMainQuantityField(), is(equalTo("")));
		    assertThat("Field 'Main Price' contains wrong data", currentPage.getTextMainPriceField(), is(equalTo("")));	 
		    
		//  8. No any error classes should be presented in html in Main Fields  
		    assertThat("Error in Field 'Main Product Name'", currentPage.getMainProductNameFieldClass(), is(equalTo("input-field")));
		    assertThat("Error in Field 'Main Quantity'", currentPage.getMainQuantityFieldClass(), is(equalTo("input-field")));
		    assertThat("Error in Field 'Main Price'", currentPage.getMainPriceFieldClass(), is(equalTo("input-field")));
		    }

		    
// Test 1.2: Calculating Total price of products, two items (positive test)
		    
	//	    @Test
	    public void verifyCorrectingCalculatingTotalPrice() {
		InvoicePage currentPage = new InvoicePage(driver).LoadPage();
    	       int quantityRandom1 = currentPage.random();
    	       int priceRandom1 = currentPage.random();
		currentPage.AddNewProduct("Notebook Dell 5151", "pcs", Integer.toString(quantityRandom1), Integer.toString(priceRandom1));
		try{Thread.sleep(2000);} catch(Exception e){}
		    assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable());
               int quantityRandom2 = currentPage.random();
               int priceRandom2 = currentPage.random(); 
		currentPage.AddNewProduct("Potaitos", "kg", Integer.toString(quantityRandom2), Integer.toString(priceRandom2));
		try{Thread.sleep(2000);} catch(Exception e){}
		    currentPage.waitUntilAvailable();
		    assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable());
		       
        //  Total price (from all Total column) should be calculated correctly
   	           int totalPriceResult = (quantityRandom1 * priceRandom1) + (quantityRandom2 * priceRandom2);
   	        assertThat("Error result in field 'Total Price' in Invoice Table", currentPage.isTextTotalPriceFieldIsCorrect(), is(equalTo(Integer.toString(totalPriceResult)))); 
		   } 

		    
		     
// Test 2: New product, product didn't added incorrectly, one item (negative test) -> Skipped
		    
	//	    @Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class)
	    public void verifyIncorrectingAddingProduct(Map<String, String> testData) {
		InvoicePage currentPage = new InvoicePage(driver).LoadPage().AddNewProduct(testData.get("product_name"), testData.get("units"), testData.get("quantity"), testData.get("price"));
		
		try{Thread.sleep(2000);} catch(Exception e){}
        
            assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable());
        
//	        1) No any Row should be added to Invoice Table
//	        2) No any buttons "Edit Product" and "Delete Product" should be presented in Invoice Table,
	    
//	        3. Total price (from all rows in Total column) shouldn't be changed,
	        assertThat("Error result in field 'Total Price' in Invoice Table", currentPage.isTextTotalPriceFieldIsCorrect(), is(equalTo("0"))); 
        
//	        4. Main Input fields should remember text that was entered to them before,		    
//	        assertThat("Field 'Main Product Name' contains wrong data", currentPage.getTextMainProductNameField(), is(equalTo(testData.get("product_name"))));
//	        assertThat("Field 'Main Quantity' contains wrong data", currentPage.getTextMainQuantityField(), is(equalTo(testData.get("quantity"))));
//	        assertThat("Field 'Main Price' contains wrong data", currentPage.getTextMainPriceField(), is(equalTo(testData.get("price"))));
	    
//	        5. No any edit classes should be presented in html of page,
//	        6. Error classes should be presented in html of page.
	        assertThat("Error in Field 'Main Product Name'", currentPage.getMainProductNameFieldClass(), is(equalTo("input-field error")));
	        assertThat("Error in Field 'Main Quantity'", currentPage.getMainQuantityFieldClass(), is(equalTo("input-field error")));
	        assertThat("Error in Field 'Main Price'", currentPage.getMainPriceFieldClass(), is(equalTo("input-field error")));
    }       
	    

//-------------------------------------- EDIT ADDED PRODUCT ----------------------------------------------------------------------------	
	        
// Test 3: Edit mode should be enabled after clicking button "Edit product" (positive test)
	     	
	//   	    @Test
	    public void verifyIsEditModeEnabled() {
	    InvoicePage currentPage = new InvoicePage(driver).LoadPage();
	        String productNumber = "1";
	        String productName = "Notebook Dell 5151";
	        String units = "pcs";
	        String quantity = "5";
	        String price = "5000";
	        String totalPrice = "25000";
	 	currentPage.AddNewProduct(productName, units, quantity, price);
 	    try{Thread.sleep(2000);} catch(Exception e){}	
	 	    assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable()); 
	 	currentPage.clickEditProductButton();
	 	try{Thread.sleep(2000);} catch(Exception e){}	
	 	currentPage.waitUntilAvailable();
	 	    
//	 	    1) Text in row in Edit mode should be the same like without Edit mode,
//	 	    2) Total of product shouldn't be changed, getTextTableTotalFieldInEdit()
	 	    assertThat("Field 'Numder' contains wrong data", currentPage.getTextTableNumberFieldInEdit(), is(equalTo(productNumber)));	
	 	    assertThat("Field 'Product Name' contains wrong data", currentPage.getTextTableProductNameFieldInEdit(), is(equalTo(productName)));
	 	    assertThat("Field 'Units' contains wrong data", currentPage.getTextTableUnitsFieldInEdit(), is(equalTo(units)));
		    assertThat("Field 'Quantity' contains wrong data", currentPage.getTextTableQuantityFieldInEdit(), is(equalTo(quantity)));
		    assertThat("Field 'Price' contains wrong data", currentPage.getTextTablePriceFieldInEdit(), is(equalTo(price)));	
		    assertThat("Field 'Total' contains wrong data", currentPage.getTextTableTotalFieldInEdit(), is(equalTo(totalPrice)));	
			
//	 	    3. Total Price of table shouldn't be changed,
	 	    assertThat("Error result in field 'Total Price' in Invoice Table", currentPage.isTextTotalPriceFieldIsCorrect(), is(equalTo(totalPrice))); 
			 
//	 	    4. Buttons "Accept changes" and "Cancel changes" should be appeared in Invoice Table,
			assertThat("Button 'Accept Changes' absent on the page", currentPage.isAcceptChangesButtonPresents());
			assertThat("Button 'Cancel Changes' absent on the page", currentPage.isCancelChangesButtonPresents());
			
//	 	    5. Input fields (Main) should be empty during editing product,
		    assertThat("Field 'Main Product Name' contains wrong data", currentPage.getTextMainProductNameField(), is(equalTo("")));
		    assertThat("Field 'Main Quantity' contains wrong data", currentPage.getTextMainQuantityField(), is(equalTo("")));
		    assertThat("Field 'Main Price' contains wrong data", currentPage.getTextMainPriceField(), is(equalTo("")));
		      
//	 	    6. Edit classes should be presented in html of page (Invoice Table),
		    assertThat("Field 'Product Name' is not in edit mode in Invoice Table", currentPage.getTableProductNameFieldEditClass(), is(equalTo("edit-input wide-edit")));
		    assertThat("Field 'Units' is not in edit mode in Invoice Table", currentPage.getTableUnitsFieldEditClass(), is(equalTo("tr-cell units editable-cell")));
		    assertThat("Field 'Quantity' is not in edit mode in Invoice Table", currentPage.getTableQuantityFieldEditClass(), is(equalTo("edit-input narrow-edit")));
		    assertThat("Field 'Price' is not in edit mode in Invoice Table", currentPage.getTablePriceFieldEditClass(), is(equalTo("edit-input narrow-edit")));
		    assertThat("Field 'Total' is not in edit mode in Invoice Table", currentPage.getTableTotalFieldEditClass(), is(equalTo("tr-cell total editable-cell")));
		    
//	 	    7. No any error classes should be presented in html of page (Main fields).
	 	    assertThat("Error in Field 'Main Product Name'", currentPage.getMainProductNameFieldClass(), is(equalTo("input-field")));
		    assertThat("Error in Field 'Main Quantity'", currentPage.getMainQuantityFieldClass(), is(equalTo("input-field")));
		    assertThat("Error in Field 'Main Price'", currentPage.getMainPriceFieldClass(), is(equalTo("input-field")));
		    } 
	   	    
	   	    
// Test 4: No any changes in edit fields in Invoice Table -> click button "Accept Changes"
	    
//	   	   @Test
	    public void verifyAcceptChangesButtonClick() {
	    InvoicePage currentPage = new InvoicePage(driver).LoadPage();
	        String productNumber = "1";
	        String productName = "Cannon Pixma 540";
	        String units = "pcs";
	        String quantity = "12";
	        String price = "11100";
	        String totalPrice = "133200";
	 	currentPage.AddNewProduct(productName, units, quantity, price);
 	    try{Thread.sleep(2000);} catch(Exception e){}	
	 	    assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable()); 
	 	currentPage.clickEditProductButton();
		try{Thread.sleep(2000);} catch(Exception e){}	
	 	currentPage.waitUntilAvailable();
	 	currentPage.clickAcceptChangesButton();
	 	        
	 		//  1. Text in Row with edited product shouldn't be changed in Invoice Table,
			//  2. Price of edited product shouldn't be changed", 
			//  7. No any edit/error classes should be presented in html in Invoice Table, 
	        //  assertThat("Wrong Product Name in Invoice Table", testData.get("product_name"), is(equalTo(currentPage.getTableRowById("tr-cell product"))));
				assertThat("Wrong Number in Invoice Table", productNumber, is(equalTo(currentPage.isTextInNumberFieldIsCorrect())));
			    assertThat("Wrong Product Name in Invoice Table", productName, is(equalTo(currentPage.isTextInProductNameFieldIsCorrect())));
				assertThat("Wrong Units in Invoice Table", units, is(equalTo(currentPage.isTextInUnitsFieldIsCorrect())));
				assertThat("Wrong Quantity in Invoice Table", quantity, is(equalTo(currentPage.isTextInQuantityFieldIsCorrect())));
				assertThat("Wrong Price in Invoice Table", price, is(equalTo(currentPage.isTextInPriceFieldIsCorrect())));
				assertThat("Wrong Total in Invoice Table", totalPrice, is(equalTo(currentPage.isTextTotalFieldIsCorrect())));
			
		    //  3. Total price (from all rows in Total column) should be calculated correctly
				assertThat("Error result in field 'Total Price' in Invoice Table", currentPage.isTextTotalPriceFieldIsCorrect(), is(equalTo(totalPrice))); 
				   
			//  4. Buttons "Add Product", "Edit Product", "Delete Product" should be present on Page,
				assertThat("Button 'Add Product' absent on the page", currentPage.isAddProductButtonPresents());
				assertThat("Button 'Edit Product' absent on the page", currentPage.isEditProductButtonPresents());
				assertThat("Button 'Delete Product' absent on the page", currentPage.isDeleteProductButtonPresents());
				
			//  5. Fields 'Main Product Name', 'Main Quantity' and 'Main Price' should be empty after adding new product 
			    assertThat("Field 'Main Product Name' contains wrong data", currentPage.getTextMainProductNameField(), is(equalTo("")));
			    assertThat("Field 'Main Quantity' contains wrong data", currentPage.getTextMainQuantityField(), is(equalTo("")));
			    assertThat("Field 'Main Price' contains wrong data", currentPage.getTextMainPriceField(), is(equalTo("")));	 
			    
			//  6. No any error classes should be presented in html in Main Fields  
			    assertThat("Error in Field 'Main Product Name'", currentPage.getMainProductNameFieldClass(), is(equalTo("input-field")));
			    assertThat("Error in Field 'Main Quantity'", currentPage.getMainQuantityFieldClass(), is(equalTo("input-field")));
			    assertThat("Error in Field 'Main Price'", currentPage.getMainPriceFieldClass(), is(equalTo("input-field")));
	    }
			    
	   	    
// Test 5: No any changes in edit fields in Invoice Table -> click button "Cancel Changes"	  
	//   	   @Test
	    public void verifyCancelChangesButtonClick() {
	    InvoicePage currentPage = new InvoicePage(driver).LoadPage();
	        String productNumber = "1";
	        String productName = "Paper roll";
	        String units = "m";
	        String quantity = "12";
	        String price = "26";
	        String totalPrice = "312";
	 	currentPage.AddNewProduct(productName, units, quantity, price);
 	    try{Thread.sleep(2000);} catch(Exception e){}	
	 	    assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable()); 
	 	    currentPage.clickEditProductButton();
	 	try{Thread.sleep(2000);} catch(Exception e){}	
	 	currentPage.waitUntilAvailable();
	 	currentPage.clickCancelChangesButton();
	 	   
	 		//  1. Text in Row with edited product shouldn't be changed in Invoice Table,
			//  2. Price of edited product shouldn't be changed", 
			//  7. No any edit/error classes should be presented in html in Invoice Table, 
	        //  assertThat("Wrong Product Name in Invoice Table", testData.get("product_name"), is(equalTo(currentPage.getTableRowById("tr-cell product"))));
				assertThat("Wrong Number in Invoice Table", productNumber, is(equalTo(currentPage.isTextInNumberFieldIsCorrect())));
			    assertThat("Wrong Product Name in Invoice Table", productName, is(equalTo(currentPage.isTextInProductNameFieldIsCorrect())));
				assertThat("Wrong Units in Invoice Table", units, is(equalTo(currentPage.isTextInUnitsFieldIsCorrect())));
				assertThat("Wrong Quantity in Invoice Table", quantity, is(equalTo(currentPage.isTextInQuantityFieldIsCorrect())));
				assertThat("Wrong Price in Invoice Table", price, is(equalTo(currentPage.isTextInPriceFieldIsCorrect())));
				assertThat("Wrong Total in Invoice Table", totalPrice, is(equalTo(currentPage.isTextTotalFieldIsCorrect())));
			
		    //  3. Total price (from all rows in Total column) should be calculated correctly
				assertThat("Error result in field 'Total Price' in Invoice Table", currentPage.isTextTotalPriceFieldIsCorrect(), is(equalTo(totalPrice))); 
				   
			//  4. Buttons "Add Product", "Edit Product", "Delete Product" should be present on Page,
				assertThat("Button 'Add Product' absent on the page", currentPage.isAddProductButtonPresents());
				assertThat("Button 'Edit Product' absent on the page", currentPage.isEditProductButtonPresents());
				assertThat("Button 'Delete Product' absent on the page", currentPage.isDeleteProductButtonPresents());
				
			//  5. Fields 'Main Product Name', 'Main Quantity' and 'Main Price' should be empty after adding new product 
			    assertThat("Field 'Main Product Name' contains wrong data", currentPage.getTextMainProductNameField(), is(equalTo("")));
			    assertThat("Field 'Main Quantity' contains wrong data", currentPage.getTextMainQuantityField(), is(equalTo("")));
			    assertThat("Field 'Main Price' contains wrong data", currentPage.getTextMainPriceField(), is(equalTo("")));	 
			    
			//  6. No any error classes should be presented in html in Main Fields  
			    assertThat("Error in Field 'Main Product Name'", currentPage.getMainProductNameFieldClass(), is(equalTo("input-field")));
			    assertThat("Error in Field 'Main Quantity'", currentPage.getMainQuantityFieldClass(), is(equalTo("input-field")));
			    assertThat("Error in Field 'Main Price'", currentPage.getMainPriceFieldClass(), is(equalTo("input-field")));
	    }
	 	   
/*	   	    
// Test 6: Changing dates to correct in edit fields in Invoice Table -> click button "Accept Changes" (positive test) -> Skipped
	    
	         @Test (dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class)	
	    public void verifyManyAcceptChangesPositive(Map<String, String> testData) {
	    InvoicePage currentPage = new InvoicePage(driver).LoadPage();
	        String productName = "Cannon Pixma 540";
	        String units = "pcs";
	        String quantity = "12";
	        String price = "11100";
	 	currentPage.AddNewProduct(productName, units, quantity, price);
 	    try{Thread.sleep(2000);} catch(Exception e){}	
	 	    assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable()); 
	 	currentPage.clickEditProductButton();
		currentPage.EditAddedProduct(testData.get("product_name"), testData.get("units"), testData.get("quantity"), testData.get("price"));
	 	try{Thread.sleep(2000);} catch(Exception e){}	
	 	currentPage.waitUntilAvailable();
		   	   }	
*/   
	   	    
// Test 7: Changing dates to correct in edit fields in Invoice Table -> click button  "Cancel Changes" (positive test)
	    
     //	    @Test
	    public void verifyEditingCancelChangespositive() {
	    InvoicePage currentPage = new InvoicePage(driver).LoadPage();
	        String productNumber = "1";
	        String productName = "Paper roll";
	        String units = "m";
	        String quantity = "12";
	        String price = "26";
	        String totalPrice = "312";
	 	currentPage.AddNewProduct(productName, units, quantity, price);
 	    try{Thread.sleep(2000);} catch(Exception e){}	
	 	    assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable()); 
	 	currentPage.clickEditProductButton();
	 	try{Thread.sleep(2000);} catch(Exception e){}	
	 	currentPage.waitUntilAvailable();
		currentPage.EditAndCancelAddedProduct("Butter", "kg", "50", "200");
 	    currentPage.waitUntilAvailable();
		try{Thread.sleep(2000);} catch(Exception e){}	
	 	   
	 		//  1. Text in Row with edited product shouldn't be changed in Invoice Table,
			//  2. Price of edited product shouldn't be changed", 
			//  7. No any edit/error classes should be presented in html in Invoice Table, 
	        	assertThat("Wrong Number in Invoice Table", productNumber, is(equalTo(currentPage.isTextInNumberFieldIsCorrect())));
			    assertThat("Wrong Product Name in Invoice Table", productName, is(equalTo(currentPage.isTextInProductNameFieldIsCorrect())));
				assertThat("Wrong Units in Invoice Table", units, is(equalTo(currentPage.isTextInUnitsFieldIsCorrect())));
				assertThat("Wrong Quantity in Invoice Table", quantity, is(equalTo(currentPage.isTextInQuantityFieldIsCorrect())));
				assertThat("Wrong Price in Invoice Table", price, is(equalTo(currentPage.isTextInPriceFieldIsCorrect())));
				assertThat("Wrong Total in Invoice Table", totalPrice, is(equalTo(currentPage.isTextTotalFieldIsCorrect())));
			
		    //  3. Total price (from all rows in Total column) should be calculated correctly
				assertThat("Error result in field 'Total Price' in Invoice Table", currentPage.isTextTotalPriceFieldIsCorrect(), is(equalTo(totalPrice))); 
				   
			//  4. Buttons "Add Product", "Edit Product", "Delete Product" should be present on Page,
				assertThat("Button 'Add Product' absent on the page", currentPage.isAddProductButtonPresents());
				assertThat("Button 'Edit Product' absent on the page", currentPage.isEditProductButtonPresents());
				assertThat("Button 'Delete Product' absent on the page", currentPage.isDeleteProductButtonPresents());
				
			//  5. Fields 'Main Product Name', 'Main Quantity' and 'Main Price' should be empty after adding new product 
			    assertThat("Field 'Main Product Name' contains wrong data", currentPage.getTextMainProductNameField(), is(equalTo("")));
			    assertThat("Field 'Main Quantity' contains wrong data", currentPage.getTextMainQuantityField(), is(equalTo("")));
			    assertThat("Field 'Main Price' contains wrong data", currentPage.getTextMainPriceField(), is(equalTo("")));	 
			    
			//  6. No any error classes should be presented in html in Main Fields  
			    assertThat("Error in Field 'Main Product Name'", currentPage.getMainProductNameFieldClass(), is(equalTo("input-field")));
			    assertThat("Error in Field 'Main Quantity'", currentPage.getMainQuantityFieldClass(), is(equalTo("input-field")));
			    assertThat("Error in Field 'Main Price'", currentPage.getMainPriceFieldClass(), is(equalTo("input-field")));
	    }	    
	   	    
// Test 8: Changing dates to incorrect in edit fields in Invoice Table -> click button "Accept Changes" (negative test)	 
	   	    
// Test 9: Changing dates to incorrect in edit fields in Invoice Table -> click button "Cancel Changes" (negative test)	

	//    @Test
	    public void verifyEditingCancelChangesNegative() {
	    InvoicePage currentPage = new InvoicePage(driver).LoadPage();
	        String productNumber = "1";
	        String productName = "Paper roll";
	        String units = "m";
	        String quantity = "12";
	        String price = "26";
	        String totalPrice = "312";
	 	currentPage.AddNewProduct(productName, units, quantity, price);
 	    try{Thread.sleep(2000);} catch(Exception e){}	
	 	    assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable()); 
	 	currentPage.clickEditProductButton();
	 	currentPage.waitUntilAvailable();
	 	try{Thread.sleep(2000);} catch(Exception e){}	
		currentPage.EditAndCancelAddedProduct("", "kg", "", "");
 	    currentPage.waitUntilAvailable();
		try{Thread.sleep(2000);} catch(Exception e){}	
	 	   
	 		//  1. Text in Row with edited product shouldn't be changed in Invoice Table,
			//  2. Price of edited product shouldn't be changed", 
			//  7. No any edit/error classes should be presented in html in Invoice Table, 
	        	assertThat("Wrong Number in Invoice Table", productNumber, is(equalTo(currentPage.isTextInNumberFieldIsCorrect())));
			    assertThat("Wrong Product Name in Invoice Table", productName, is(equalTo(currentPage.isTextInProductNameFieldIsCorrect())));
				assertThat("Wrong Units in Invoice Table", units, is(equalTo(currentPage.isTextInUnitsFieldIsCorrect())));
				assertThat("Wrong Quantity in Invoice Table", quantity, is(equalTo(currentPage.isTextInQuantityFieldIsCorrect())));
				assertThat("Wrong Price in Invoice Table", price, is(equalTo(currentPage.isTextInPriceFieldIsCorrect())));
				assertThat("Wrong Total in Invoice Table", totalPrice, is(equalTo(currentPage.isTextTotalFieldIsCorrect())));
			
		    //  3. Total price (from all rows in Total column) should be calculated correctly
				assertThat("Error result in field 'Total Price' in Invoice Table", currentPage.isTextTotalPriceFieldIsCorrect(), is(equalTo(totalPrice))); 
				   
			//  4. Buttons "Add Product", "Edit Product", "Delete Product" should be present on Page,
				assertThat("Button 'Add Product' absent on the page", currentPage.isAddProductButtonPresents());
				assertThat("Button 'Edit Product' absent on the page", currentPage.isEditProductButtonPresents());
				assertThat("Button 'Delete Product' absent on the page", currentPage.isDeleteProductButtonPresents());
				
			//  5. Fields 'Main Product Name', 'Main Quantity' and 'Main Price' should be empty after adding new product 
			    assertThat("Field 'Main Product Name' contains wrong data", currentPage.getTextMainProductNameField(), is(equalTo("")));
			    assertThat("Field 'Main Quantity' contains wrong data", currentPage.getTextMainQuantityField(), is(equalTo("")));
			    assertThat("Field 'Main Price' contains wrong data", currentPage.getTextMainPriceField(), is(equalTo("")));	 
			    
			//  6. No any error classes should be presented in html in Main Fields  
			    assertThat("Error in Field 'Main Product Name'", currentPage.getMainProductNameFieldClass(), is(equalTo("input-field")));
			    assertThat("Error in Field 'Main Quantity'", currentPage.getMainQuantityFieldClass(), is(equalTo("input-field")));
			    assertThat("Error in Field 'Main Price'", currentPage.getMainPriceFieldClass(), is(equalTo("input-field")));
	    }	   	   
	    
	    
// Test 10: Changing dates to incorrect in edit fields in Invoice Table -> click "Accept Changes" -> click button "Cancel Changes" (negative test)	

	//    @Test
	    public void verifyEditingAccept_CancelChangesNegative() {
	    InvoicePage currentPage = new InvoicePage(driver).LoadPage();
	        String productNumber = "1";
	        String productName = "Paper roll";
	        String units = "m";
	        String quantity = "12";
	        String price = "26";
	        String totalPrice = "312";
	 	currentPage.AddNewProduct(productName, units, quantity, price);
 	    try{Thread.sleep(2000);} catch(Exception e){}	
	 	    assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable()); 
	 	currentPage.clickEditProductButton();
	 	currentPage.waitUntilAvailable();
	 	try{Thread.sleep(2000);} catch(Exception e){}	
		currentPage.EditAndAcceptAddedProduct("", "kg", "", "");
 	    currentPage.waitUntilAvailable();
		try{Thread.sleep(2000);} catch(Exception e){}
		currentPage.clickCancelChangesButton();
	 	   
	 		//  1. Text in Row with edited product shouldn't be changed in Invoice Table,
			//  2. Price of edited product shouldn't be changed", 
			//  7. No any edit/error classes should be presented in html in Invoice Table, 
	        	assertThat("Wrong Number in Invoice Table", productNumber, is(equalTo(currentPage.isTextInNumberFieldIsCorrect())));
			    assertThat("Wrong Product Name in Invoice Table", productName, is(equalTo(currentPage.isTextInProductNameFieldIsCorrect())));
				assertThat("Wrong Units in Invoice Table", units, is(equalTo(currentPage.isTextInUnitsFieldIsCorrect())));
				assertThat("Wrong Quantity in Invoice Table", quantity, is(equalTo(currentPage.isTextInQuantityFieldIsCorrect())));
				assertThat("Wrong Price in Invoice Table", price, is(equalTo(currentPage.isTextInPriceFieldIsCorrect())));
				assertThat("Wrong Total in Invoice Table", totalPrice, is(equalTo(currentPage.isTextTotalFieldIsCorrect())));
			
		    //  3. Total price (from all rows in Total column) should be calculated correctly
				assertThat("Error result in field 'Total Price' in Invoice Table", currentPage.isTextTotalPriceFieldIsCorrect(), is(equalTo(totalPrice))); 
				   
			//  4. Buttons "Add Product", "Edit Product", "Delete Product" should be present on Page,
				assertThat("Button 'Add Product' absent on the page", currentPage.isAddProductButtonPresents());
				assertThat("Button 'Edit Product' absent on the page", currentPage.isEditProductButtonPresents());
				assertThat("Button 'Delete Product' absent on the page", currentPage.isDeleteProductButtonPresents());
				
			//  5. Fields 'Main Product Name', 'Main Quantity' and 'Main Price' should be empty after adding new product 
			    assertThat("Field 'Main Product Name' contains wrong data", currentPage.getTextMainProductNameField(), is(equalTo("")));
			    assertThat("Field 'Main Quantity' contains wrong data", currentPage.getTextMainQuantityField(), is(equalTo("")));
			    assertThat("Field 'Main Price' contains wrong data", currentPage.getTextMainPriceField(), is(equalTo("")));	 
			    
			//  6. No any error classes should be presented in html in Main Fields  
			    assertThat("Error in Field 'Main Product Name'", currentPage.getMainProductNameFieldClass(), is(equalTo("input-field")));
			    assertThat("Error in Field 'Main Quantity'", currentPage.getMainQuantityFieldClass(), is(equalTo("input-field")));
			    assertThat("Error in Field 'Main Price'", currentPage.getMainPriceFieldClass(), is(equalTo("input-field")));
	    }	   	   	    
	   	   
//-------------------------------------- DELETE ADDED PRODUCT ----------------------------------------------------------------------------	
	        
// Test 11: Exist one product, delete it
	   	   
	//   	   @Test
	    public void verifyDeleteProductClick() {
	    InvoicePage currentPage = new InvoicePage(driver).LoadPage();
	        String productNumber = "1";
	        String productName = "Paper roll";
	        String units = "m";
	        String quantity = "12";
	        String price = "26";
	        String totalPrice = "312";
	 	currentPage.AddNewProduct(productName, units, quantity, price);
 	    try{Thread.sleep(2000);} catch(Exception e){}	
	 	    assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable()); 
	 	currentPage.clickDeleteProductButton();	
	 	try{Thread.sleep(2000);} catch(Exception e){}	
	 	    
//	 	    1. Row with product  should be disappeared from Invoice Table,
	 //	 System.out.println(currentPage.getProductsTable().getRows().size());
//	 	    2. Total Price should be recalculated after deleting product,
	 		assertThat("Error result in field 'Total Price' in Invoice Table", currentPage.isTextTotalPriceFieldIsCorrect(), is(equalTo("0")));
	 		
//	 	    3) Buttons "Edit product" and "Delete product" shouldn't be visible after deleting product near row with deleted product in Invoice Table
	 		
//	 	    4. Input fields (Main) should be empty,
		    assertThat("Field 'Main Product Name' contains wrong data", currentPage.getTextMainProductNameField(), is(equalTo("")));
		    assertThat("Field 'Main Quantity' contains wrong data", currentPage.getTextMainQuantityField(), is(equalTo("")));
		    assertThat("Field 'Main Price' contains wrong data", currentPage.getTextMainPriceField(), is(equalTo("")));		 	    
		    
//	 	    5) No any error classes should be presented in html in Main Fields ,
//	 	    6) No any edit/error classes should be presented in html in Invoice Table.
	   	   }
	   	   
// Test 12: Exist two products, delete one -> Error (доробити) -> doesn't work
	 /*   	   @Test
	    public void verifyDeletingOneProductFromTwo() {
	    InvoicePage currentPage = new InvoicePage(driver).LoadPage();
	    currentPage.AddNewProduct("Orange", "kg", "20", "6000");
	        String productNumber = "1";
	        String productName = "Paper roll";
	        String units = "m";
	        String quantity = "12";
	        String price = "26";
	        String totalPrice = "312";
	 	currentPage.AddNewProduct(productName, units, quantity, price);
 	    try{Thread.sleep(2000);} catch(Exception e){}	
	 	    assertThat("Some of Main fields is not exist/or have error", currentPage.isAvailable()); 
	 	currentPage.waitUntilAvailable();	 	   
	 	try{Thread.sleep(2000);} catch(Exception e){}	       
	 //	currentPage.clickDeleteProductButton();	 ->  doesn't work

	   	   } 
*/	   	   
}
	

