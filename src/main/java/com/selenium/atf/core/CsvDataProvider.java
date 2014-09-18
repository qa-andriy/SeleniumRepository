package com.selenium.atf.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

import au.com.bytecode.opencsv.CSVReader;

public class CsvDataProvider {

	@DataProvider(name = "CsvDataProvider")
	public static Iterator<Object[]> provideData(Method method) {
		// Creating list where will be contained rows with data (but ever row
		// will be as one Map and we will get data from row (Map)
		// by key. All Maps will be placed in List). "List" - it is a list of
		// Maps that we will be create in the feature.
		List<Object[]> list = new ArrayList<Object[]>();

		// creating path to csv file 'Firsttest.verifyCorrectingAddingProduct'
		String pathName = "test_data" + File.separator
				+ method.getDeclaringClass().getSimpleName() + "."
				+ method.getName() + ".csv";
		File file = new File(pathName);

		try {
			CSVReader reader = new CSVReader(new FileReader(file));
			// read keys of Map from the csv file (titles of columns)
			String[] keys = reader.readNext();
			// checking because file can be empty
			if (keys != null) {
				String[] dataParts;
				// "While" will read ever next row from csv file, create Map(1
				// row = 1 Map), and add these Maps to "List"
				// reader will be read rows to time that dataParts != null
				while ((dataParts = reader.readNext()) != null) {
					// Creating Map and adding rows, from csv, file to Map
					Map<String, String> testData = new HashMap<String, String>();
					// iteration by keys for adding rows to Map (filling Map)
					for (int i = 0; i < keys.length; i++) {
						testData.put(keys[i], dataParts[i]);
					}
					// add array of objects (Maps) to Arraylist -> Converting
					// Map to array of objects
					list.add(new Object[] { testData });
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File " + pathName + " is not found.\n"
					+ e.getStackTrace().toString());
		} catch (IOException e) {
			throw new RuntimeException("Could not read " + pathName
					+ " file.\n" + e.getStackTrace().toString());
		}

		return list.iterator();
	}
}
