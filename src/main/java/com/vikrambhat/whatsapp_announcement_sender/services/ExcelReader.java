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

	private static final Logger log = LoggerFactory.getLogger(ExcelReader.class);

	public static List<String> getPhoneNumbers(String filePath) throws Exception {
		log.info("Starting with getPhoneNumbers");
		List<String> numbers = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheetAt(0);
			log.info("Working on sheet: {}", workbook.getSheetName(0));
			for (Row row : sheet) {
				Cell nameCell = row.getCell(0);
				Cell phoneNumberCell = row.getCell(1);
				log.info("Phone number found for {}", nameCell);
				log.info("cell type set to: {}", phoneNumberCell.getCellType());
				if (phoneNumberCell != null && phoneNumberCell.getCellType() == CellType.STRING) {
					String phoneNumber = phoneNumberCell.getStringCellValue().trim();

					if (Validator.isValidPhone(phoneNumber)) {
						log.info("Validation passed for {}, adding to list.", phoneNumber);

						numbers.add(phoneNumber);
					}
				}
			}
		}
		return numbers;
	}

}
