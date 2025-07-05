package com.vikrambhat.whatsapp_announcement_sender.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;

public final class Locators {
	private static final Properties P = new Properties();

	static {
		try (InputStream in = Locators.class.getClassLoader().getResourceAsStream("locators.properties")) {
			if (in == null)
				throw new IllegalStateException("locators.properties missing");
			P.load(in);
		} catch (IOException ex) {
			throw new RuntimeException("Failed to load locators", ex);
		}
	}

	private Locators() {
	}

	/**
	 * Returns a Selenium {@code By} using the selector stored under {@code key}.
	 */
	public static By by(String key) {
		String sel = P.getProperty(key);
		if (sel == null)
			throw new IllegalArgumentException("Unknown locator key: " + key);
		// decide XPath vs CSS by a prefix if you like; here we treat all as CSS
		return By.xpath(sel);
	}

	public static String raw(String key) {
		return P.getProperty(key);
	}
}
