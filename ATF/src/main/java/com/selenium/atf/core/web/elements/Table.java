package com.selenium.atf.core.web.elements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.selenium.atf.core.web.WebComponent;

public class Table extends WebComponent<Table> {

	// Get element from the Invoice Table

	public Table(WebDriver driver, By findByMethod) {
		super(driver, findByMethod);
	}

	// Get Table Row by id (Class)
	public Row getTableRowById(String id) {
		List<Row> rows = getRows();
		for (Row row : rows) {
			if ((row.getId()).equals(id)) {
				return row;
			}
		}
		return null;
	}

	// Get Table EditRow by id (Class)
	public Row getTableEditRowById(String id) {
		List<Row> rows = getEditRows();
		for (Row row : rows) {
			if ((row.getId()).equals(id)) {
				return row;
			}
		}
		return null;
	}

	// Get Table Row by Number (Using column Number)
	public Row getTableRowByNumber(String number) {
		List<Row> rows = getRows();
		for (Row row : rows) {
			if (row.getNumber().equals(number)) {
				return row;
			}
		}
		return null;
	}

	// Creating list of Rows in Invoice Table
	public List<Row> getRows() {
		List<Row> rows = new ArrayList<Row>();
		WebElement element = getWebElement();

		List<WebElement> rows_els = element.findElements(By
				.xpath("//*[@class='table-row']"));

		for (WebElement row_el : rows_els) {
			rows.add(new Row(driver, row_el));
		}
		return rows;
	}

	// Creating list of Edit Rows in Invoice Table
	public List<Row> getEditRows() {
		List<Row> rows = new ArrayList<Row>();
		WebElement element = getWebElement();

		List<WebElement> rows_els = element.findElements(By
				.xpath("//*[@class='table-row editable-row']"));

		for (WebElement row_el : rows_els) {
			rows.add(new Row(driver, row_el));
		}
		return rows;
	}

	// Get Sum of all elements from column Total
	public String getTableTotalSum() {
		List<Row> rows = getRows();
		int sum = 0;
		for (Row row : rows) {
			sum = sum + (Integer
						.parseInt(row.getTotalFieldTextFromInvoiceTable()));
		}
		return Integer.toString(sum);
	}
}
