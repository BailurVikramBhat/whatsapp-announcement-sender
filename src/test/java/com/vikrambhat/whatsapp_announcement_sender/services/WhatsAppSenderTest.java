package com.vikrambhat.whatsapp_announcement_sender.services;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import com.vikrambhat.whatsapp_announcement_sender.utils.WebDriverFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.List;

class WhatsAppSenderTest {

	@Test
	void invalidPdfPathThrows() {
		WebDriver dummy = mock(WebDriver.class); // no browser
		WebDriverFactory factory = () -> dummy;

		WhatsAppSender sender = new WhatsAppSender("bad/path.pdf", factory);
		assertThrows(IllegalArgumentException.class, () -> sender.sendTo(List.of("+919876543210")));
	}
}
