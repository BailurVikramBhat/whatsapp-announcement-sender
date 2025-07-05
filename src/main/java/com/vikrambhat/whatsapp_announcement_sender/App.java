package com.vikrambhat.whatsapp_announcement_sender;

import java.util.List;

import com.vikrambhat.whatsapp_announcement_sender.configs.Config;
import com.vikrambhat.whatsapp_announcement_sender.services.ExcelReader;
import com.vikrambhat.whatsapp_announcement_sender.services.WhatsAppSender;

public class App {
	public static void main(String[] args) {
		try {
			String excel = Config.get("excel.file.path");
			String pdf = Config.get("pdf.file.path");
			int limit = Config.getInt("max.contacts");

			List<String> phones = ExcelReader.getPhoneNumbers(excel).stream().limit(limit).toList();

			WhatsAppSender sender = new WhatsAppSender(pdf);
			sender.login();
			sender.sendTo(phones);
			sender.quit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
