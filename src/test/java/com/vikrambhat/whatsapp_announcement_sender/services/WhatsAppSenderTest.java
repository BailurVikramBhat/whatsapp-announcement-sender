package com.vikrambhat.whatsapp_announcement_sender.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import com.vikrambhat.whatsapp_announcement_sender.pages.WhatsAppChatPage;
import com.vikrambhat.whatsapp_announcement_sender.utils.WebDriverFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.List;

class WhatsAppSenderTest {

	/** Builds a workbook with one unsent, valid contact row. */
	private XSSFWorkbook workbookWithOneContact() {
		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sh = wb.createSheet();
		Row r0 = sh.createRow(0);
		r0.createCell(0).setCellValue("Test");
		r0.createCell(1).setCellValue("9876543210");
		return wb;
	}

	@Test
	void sendAndMarkStampsDate() throws Exception {
		// 1) Build workbook + contacts list
		XSSFWorkbook wb = workbookWithOneContact();
		ExcelReader.Result res = ExcelReader.loadUnsent(wb);
		List<Contact> contacts = res.contacts;

		// 2) Prepare a fake WhatsAppSender with mocked WebDriver
		WebDriver dummyDriver = mock(WebDriver.class);
		when(dummyDriver.findElements(any())).thenReturn(List.of(mock(org.openqa.selenium.WebElement.class)));

		// simple factory
		WebDriverFactory factory = () -> dummyDriver;

		WhatsAppSender sender = new WhatsAppSender("dummy.pdf", factory) {
			// override chat to no-op so we don't wait on real web page
			{
				super.chat = new WhatsAppChatPage(dummyDriver, Duration.ofMillis(10)) {
					@Override
					public void openChat(String phone) {
					}

					@Override
					public void attachAndSendPdf(String pdf) {
					}
				};
			}
		};

		// 3) Act
		sender.sendAndMark(contacts, wb);

		// 4) Assert SentOn is filled
		Row firstRow = contacts.get(0).row();
		Cell sentCell = firstRow.getCell(ExcelReader.getSentOn());
		assertNotNull(sentCell, "SentOn cell should exist");
		assertFalse(sentCell.getStringCellValue().isBlank(), "SentOn date must be stamped");
	}

	@Test
	void invalidPdfPathThrows() {
		WebDriver dummy = mock(WebDriver.class); // no browser
		WebDriverFactory factory = () -> dummy;

		WhatsAppSender sender = new WhatsAppSender("bad/path.pdf", factory);
		assertThrows(IllegalArgumentException.class, () -> sender.sendTo(List.of("+919876543210")));
	}

}
