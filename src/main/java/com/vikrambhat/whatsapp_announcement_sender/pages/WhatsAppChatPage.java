package com.vikrambhat.whatsapp_announcement_sender.pages;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vikrambhat.whatsapp_announcement_sender.utils.Locators;

public class WhatsAppChatPage {
	private static final Logger log = LoggerFactory.getLogger(WhatsAppChatPage.class);

	private final WebDriver driver;
	private final WebDriverWait wait;

	public WhatsAppChatPage(WebDriver driver, Duration timeout) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, timeout);
	}

	public void openChat(String phone) {
		this.driver.get("https://web.whatsapp.com/send?phone=" + phone);
		this.wait.until(ExpectedConditions.presenceOfElementLocated(Locators.by("plus.button.xpath")));
	}

	public void closePopupIfPresent() {
		log.info("Trying to close popup");
		List<WebElement> popup = this.driver.findElements(Locators.by("welcomePopup.xpath"));
		if (!popup.isEmpty()) {
			log.info("Popup found, closing");
			this.driver.findElement(Locators.by("welcomePopup.continueCTA.xpath")).click();
			log.info("popup closed");

		} else {
			fail("Unable to close popup, test wouldn't progress without that");
		}

	}

	public void attachAndSendPdf(String pdfPath) throws InterruptedException {
		log.info("Sending pdf");
		this.driver.findElement(Locators.by("plus.button.xpath")).click();
		this.wait.until(ExpectedConditions.presenceOfElementLocated(Locators.by("filePopOver.document.xpath")));

		WebElement fileInput = this.wait
				.until(ExpectedConditions.presenceOfElementLocated(Locators.by("fileinput.xpath")));
		fileInput.sendKeys(pdfPath);
		Thread.sleep(4_000);
		WebElement sendCTA = this.wait.until(ExpectedConditions.elementToBeClickable(Locators.by("send.button.xpath")));
		sendCTA.click();
		Thread.sleep(5_000);
		log.info("Succeessfully sent pdf");
	}

}
