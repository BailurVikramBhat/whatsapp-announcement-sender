package com.vikrambhat.whatsapp_announcement_sender.utils;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.vikrambhat.whatsapp_announcement_sender.utils.Locators;

import static org.junit.jupiter.api.Assertions.*;

class LocatorsTest {

	@Test
	void byReturnsExpectedSelector() {
		By clip = Locators.by("plus.button.xpath");
		assertTrue(clip.toString().contains("//span[@data-icon='plus-rounded']"));
	}

	@Test
	void unknownKeyThrows() {
		assertThrows(IllegalArgumentException.class, () -> Locators.by("does.not.exist"));
	}
}
