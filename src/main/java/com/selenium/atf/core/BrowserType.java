package com.selenium.atf.core;

import java.util.HashMap;
import java.util.Map;

public enum BrowserType {

	FIREFOX("firefox"), CHROME("chrome"), IE("ie");

	private String browserKey;

	// creating map with all browser. After that we will be able to find browser
	// that we need in Map by key "key" (firefox, chrome, etc).
	private static Map<String, BrowserType> BrowserMap = new HashMap<String, BrowserType>();

	// filling created map
	static {
		// method values() return array of all values (firefox, chrome, etc)
		// enumerated in enum BrowserType
		for (BrowserType bt : BrowserType.values()) {
			BrowserMap.put(bt.key(), bt);
		}
	}

	// creating constructor for enumeration
	private BrowserType(String key) {
		browserKey = key;
	}

	private String key() {
		return this.browserKey;
	}

	// method give possibility to take value by key from BrowserType
	public static BrowserType get(String key) {
		if (BrowserMap.containsKey(key)) {
			return BrowserMap.get(key);
		}
		return FIREFOX;
	}
}
