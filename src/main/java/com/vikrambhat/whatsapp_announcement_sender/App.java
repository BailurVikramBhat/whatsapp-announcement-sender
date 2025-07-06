package com.vikrambhat.whatsapp_announcement_sender;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vikrambhat.whatsapp_announcement_sender.configs.Config;
import com.vikrambhat.whatsapp_announcement_sender.services.Contact;
import com.vikrambhat.whatsapp_announcement_sender.services.ExcelReader;
import com.vikrambhat.whatsapp_announcement_sender.services.Metrics;
import com.vikrambhat.whatsapp_announcement_sender.services.WhatsAppSender;

public class App {
	public static final Logger log = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		String excel = Config.get("excel.file.path");
		String pdf = Config.get("pdf.file.path");
		int limit = Config.getInt("max.contacts");
		try (FileInputStream fis = new FileInputStream(excel);
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				Scanner sc = new Scanner(System.in)) {

			ExcelReader.Result res = ExcelReader.loadUnsent(wb);

			Metrics m = res.stats;

			log.info("Excel analytics → total:{}  valid:{}  missingPhones:{}  invalidPhones:{}  missingNames:{}",
					m.totalRows(), m.validPhones(), m.missingPhone(), m.invalidPhone(), m.missingName());

			List<Contact> today = res.contacts.stream().limit(limit).toList();
			if (today.isEmpty()) {
				log.info(
						"All valid contacts already marked as done, if you want to resend, please clear the column used for marking");
				return;
			}

			System.out.print("Would you like to proceed?: (Y/n): ");
			String resp = sc.next();
			if (!resp.equalsIgnoreCase("y")) {
				log.info("Ending the session.");
				return;
			}

			log.info("Sending the message for the {} valid contacts ->", today.size());

			WhatsAppSender sender = new WhatsAppSender(pdf);
			sender.login();
			sender.sendAndMark(today, wb);
//			sender.quit();
			try (FileOutputStream out = new FileOutputStream(Paths.get(excel).toFile())) {
				wb.write(out);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}