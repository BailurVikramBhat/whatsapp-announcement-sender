package com.vikrambhat.whatsapp_announcement_sender.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validator {

	private static final Logger log = LoggerFactory.getLogger(Validator.class);

	private Validator() {
	}

	public static boolean isValidPhone(String n) {
		log.info("validating for {}", n);
		return n != null && n.matches("\\d{10}");
	}

	public static boolean fileExists(String path) {
		return new File(path).exists();
	}

	public static boolean isPdf(String path) {
		return path != null && path.toLowerCase().endsWith(".pdf");
	}
}
