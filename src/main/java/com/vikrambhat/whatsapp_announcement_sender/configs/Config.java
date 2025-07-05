package com.vikrambhat.whatsapp_announcement_sender.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
	private static final Properties PROPS = new Properties();
	static {
		try (InputStream in = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
			if (in == null)
				throw new IllegalStateException("application.properties missing");
			PROPS.load(in);
		} catch (IOException ex) {
			throw new RuntimeException("Failed to load configuration", ex);
		}
	}

	private Config() {
	}

	public static String get(String key) {
		return PROPS.getProperty(key);
	}

	public static int getInt(String key) {
		return Integer.parseInt(get(key));
	}
}
