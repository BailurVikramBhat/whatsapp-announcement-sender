package com.vikrambhat.whatsapp_announcement_sender.utils;

import org.junit.jupiter.api.Test;

import com.vikrambhat.whatsapp_announcement_sender.utils.Validator;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

	@Test
	void phoneNumberFormat() {
		assertTrue(Validator.isValidPhone("9876543210"));
		assertFalse(Validator.isValidPhone("98765"));
	}

	@Test
	void pdfFileDetection() {
		assertTrue(Validator.isPdf("temple.PDF"));
		assertFalse(Validator.isPdf("temple.docx"));
	}

	@Test
	void fileExistsCheck() throws Exception {
		Path temp = Files.createTempFile("dummy", ".tmp");
		assertTrue(Validator.fileExists(temp.toString()));
		Files.delete(temp);
		assertFalse(Validator.fileExists(temp.toString()));
	}
}
