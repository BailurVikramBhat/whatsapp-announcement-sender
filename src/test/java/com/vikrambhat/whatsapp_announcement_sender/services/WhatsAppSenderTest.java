package com.vikrambhat.whatsapp_announcement_sender.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhatsAppSenderTest {

	@Test
	void invalidPdfPathThrows() {
		WhatsAppSender sender = new WhatsAppSender("bad/path.pdf");
		assertThrows(IllegalArgumentException.class, () -> sender.sendTo(java.util.List.of("+919876543210")));
		sender.quit();
	}
}
