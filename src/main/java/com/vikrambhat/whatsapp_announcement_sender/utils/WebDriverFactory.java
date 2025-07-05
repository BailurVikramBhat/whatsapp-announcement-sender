package com.vikrambhat.whatsapp_announcement_sender.utils;

import org.openqa.selenium.WebDriver;

@FunctionalInterface
public interface WebDriverFactory {
	WebDriver create();
}
