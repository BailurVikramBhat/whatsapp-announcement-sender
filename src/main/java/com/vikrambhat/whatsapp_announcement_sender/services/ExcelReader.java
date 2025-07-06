package com.vikrambhat.whatsapp_announcement_sender.services;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vikrambhat.whatsapp_announcement_sender.utils.Validator;

public class ExcelReader {

	public static class Result {
		public final List<Contact> contacts;
		public final Metrics stats;

		public Result(List<Contact> c, Metrics s) {
			contacts = c;
			stats = s;
		}
	}

	private static final Logger log = LoggerFactory.getLogger(ExcelReader.class);

	private static final int COL_NAME = 0;
	private static final int COL_PHONE = 1;
	private static final int COL_SENTON = 2;

	public static Result loadUnsent(XSSFWorkbook wb) throws Exception {
		int total = 0, valid = 0, missingPhone = 0, invalidPhone = 0, missingName = 0;
		List<Contact> contacts = new ArrayList<>();

		Sheet sheet = wb.getSheetAt(0);

		for (Row r : sheet) {

			total++;
			String name = getString(r, COL_NAME);
			String phone = getString(r, COL_PHONE);
			if (r.getRowNum() == 0 && phone.equalsIgnoreCase("Phone Number")) {
				continue;
			}
			if (phone == null || phone.isBlank()) {
				missingPhone++;
				continue;
			}

			if (!Validator.isValidPhone(phone)) {
				invalidPhone++;
				continue;
			}

			if (name == null || name.isBlank())
				missingName++;

			// skip if SentOn already filled
			if (!isBlank(r.getCell(COL_SENTON)))
				continue;
			valid++;
			contacts.add(new Contact(name, phone, r));

		}

		Metrics m = new Metrics(total, valid, missingPhone, invalidPhone, missingName);
		return new Result(contacts, m);
	}

	private static String getString(Row r, int col) {
		Cell c = r.getCell(col);
		return c == null ? null : c.getStringCellValue().trim();
	}

	private static boolean isBlank(Cell c) {
		return c == null || c.getCellType() == CellType.BLANK || c.getStringCellValue().isBlank();
	}

	public static int getSentOn() {
		return COL_SENTON;
	}

}
