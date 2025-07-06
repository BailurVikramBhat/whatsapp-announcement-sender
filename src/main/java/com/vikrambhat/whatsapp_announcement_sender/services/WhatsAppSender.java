package com.vikrambhat.whatsapp_announcement_sender.services;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vikrambhat.whatsapp_announcement_sender.pages.WhatsAppChatPage;
import com.vikrambhat.whatsapp_announcement_sender.utils.DelayUtil;
import com.vikrambhat.whatsapp_announcement_sender.utils.Validator;
import com.vikrambhat.whatsapp_announcement_sender.utils.WebDriverFactory;

import java.io.FileOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WhatsAppSender {
	private static final Logger log = LoggerFactory.getLogger(WhatsAppSender.class);

	private final WebDriver driver;
	protected WhatsAppChatPage chat;
	private final String pdfPath;

	public WhatsAppSender(String pdfPath) {
		this.pdfPath = pdfPath;

		WebDriverManager.chromedriver().setup();
		this.driver = new ChromeDriver();
		this.chat = new WhatsAppChatPage(this.driver, Duration.ofSeconds(20));
		log.debug("ChromeDriver ready");
	}

	public WhatsAppSender(String pdfPath, WebDriverFactory factory) {
		this.pdfPath = pdfPath;
		this.driver = factory.create();
		this.chat = new WhatsAppChatPage(this.driver, Duration.ofSeconds(10));
	}

	public void login() throws InterruptedException {
		this.driver.get("https://web.whatsapp.com");
		log.info("Waiting 20 s for QR-code scan …");
		Thread.sleep(50_000);
		this.chat.closePopupIfPresent();
	}

	public void sendTo(List<String> phones) throws Exception {
		if (!Validator.fileExists(this.pdfPath) || !Validator.isPdf(this.pdfPath))
			throw new IllegalArgumentException("Bad PDF: " + this.pdfPath);

		for (String num : phones) {
			try {
				this.chat.openChat(num);
				this.chat.attachAndSendPdf(this.pdfPath);
				log.info("✓ Sent to: {}", num);
				DelayUtil.randomSleep();
			} catch (Exception ex) {
				log.error("✗ Failed for {} with exception: {}", num, ex);
			}
		}
	}

	public void sendAndMark(List<Contact> contacts, XSSFWorkbook wb) throws Exception {

		DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE_TIME;
		String today = LocalDateTime.now().format(fmt);

		for (Contact c : contacts) {
			try {
				chat.openChat(c.phone());
				chat.attachAndSendPdf(pdfPath);

				Cell sentCell = c.row().createCell(ExcelReader.getSentOn(), CellType.STRING);
				sentCell.setCellValue(today);

				log.info("✓ Sent to {}", c.phone());
			} catch (Exception ex) {
				log.error("✗ {}", c.phone(), ex);
			}
			DelayUtil.randomSleep();
		}

	}

	public void quit() {
		if (this.driver != null)
			this.driver.quit();
	}
}
