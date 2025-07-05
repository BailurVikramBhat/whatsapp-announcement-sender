package com.vikrambhat.whatsapp_announcement_sender.services;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelReaderTest {

	@Test
	void readsValidFile() throws Exception {

		Path tmp = Files.createTempFile("contacts", ".xlsx");
		try (Workbook wb = new XSSFWorkbook()) {
			Sheet sh = wb.createSheet();
			Row r = sh.createRow(0);
			r.createCell(0).setCellValue("Test");
			r.createCell(1).setCellValue("9876543210");
			try (var out = Files.newOutputStream(tmp)) {
				wb.write(out);
			}
		}

		List<String> nums = ExcelReader.getPhoneNumbers(tmp.toString());
		assertEquals(List.of("9876543210"), nums);

		Files.delete(tmp);
	}

	@Test
	void missingFileThrows() {
		assertThrows(Exception.class, () -> ExcelReader.getPhoneNumbers("nope.xlsx"));
	}
}
