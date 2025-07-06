package com.vikrambhat.whatsapp_announcement_sender.configs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

	@SuppressWarnings("static-method")
	@Test
	void loadsKnownKey() {
		assertEquals("100", Config.get("max.contacts"));
	}

	@SuppressWarnings("static-method")
	@Test
	void missingKeyReturnsNull() {
		assertNull(Config.get("no.such.key"));
	}

	@SuppressWarnings("static-method")
	@Test
	void nonNumericValueThrows() {
		assertThrows(NumberFormatException.class, () -> Config.getInt("excel.file.path"));
	}
}
