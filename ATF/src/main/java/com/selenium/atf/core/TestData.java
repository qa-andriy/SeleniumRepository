package com.selenium.atf.core;

import static com.selenium.atf.core.TestHelper.random;

public class TestData {
	
	//private static final String PAGE_URL = "file:///C:/Selenium%20Task/html_invoice-master/index.html";
	
	// Product 1
	private static String productNumber1 = "1";  
	private static String productName1 = "Phone Nokia Lumia 910";
	private static String units1 = "pcs";
	private static int quantity1 = random();
	private static int price1 = random();
	private static int total1 = quantity1 * price1;
	
	// Product 2
	private static String productNumber2 = "2";
	private static String productName2 = "Potatos";
	private static String units2 = "m";
	private static int quantity2 = random();
	private static int price2 = random();
	private static int total2 = quantity2 * price2;

	private static int totalPriceResult = (quantity1 * price1) + (quantity2 * price2);
	
	
//	  public static String getPageURL() {
//		    return PAGE_URL;
//		  }
	  
	  public static String getProductNumber1() {
		    return productNumber1;
		  }
	  
	  public static String getProductName1() {
		    return productName1;
		  }
	  
	  public static String getUnits1() {
		    return units1;
		  }
	  
	  public static int getQuantity1() {
		    return quantity1;
		  }
	  
	  public static int getPrice1() {
		    return price1;
		  }
	  
	  public static int getTotal1() {
		    return total1;
		  }
	  
	  public static String getProductNumber2() {
		    return productNumber2;
		  }
	  
	  public static String getProductName2() {
		    return productName2;
		  }
	  
	  public static String getUnits2() {
		    return units2;
		  }
	  
	  public static int getQuantity2() {
		    return quantity2;
		  }
	  
	  public static int getPrice2() {
		    return price2;
		  }
	  
	  public static int getTotal2() {
		    return total2;
		  }
	  
	  public static int getTotalPriceResult() {
		    return totalPriceResult;
		  }
}
