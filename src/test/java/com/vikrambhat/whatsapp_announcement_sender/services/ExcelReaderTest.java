package com.vikrambhat.whatsapp_announcement_sender.services;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelReaderTest {

	private XSSFWorkbook buildSampleBook() {
		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sh = wb.createSheet();

		// Row 0 – valid, unsent
		Row r0 = sh.createRow(0);
		r0.createCell(0).setCellValue("Rama");
		r0.createCell(1).setCellValue("9876543210");

		// Row 1 – missing phone
		Row r1 = sh.createRow(1);
		r1.createCell(0).setCellValue("NoPhone");

		// Row 2 – invalid phone
		Row r2 = sh.createRow(2);
		r2.createCell(0).setCellValue("BadPhone");
		r2.createCell(1).setCellValue("12345");

		// Row 3 – missing name but valid phone
		Row r3 = sh.createRow(3);
		r3.createCell(1).setCellValue("9123456789");

		// Row 4 – already sent
		Row r4 = sh.createRow(4);
		r4.createCell(0).setCellValue("SentAlready");
		r4.createCell(1).setCellValue("9000000001");
		r4.createCell(ExcelReader.getSentOn()).setCellValue("2025-07-05");

		return wb;
	}

	@Test
	void loadUnsentComputesMetricsCorrectly() throws Exception {
		XSSFWorkbook wb = buildSampleBook();
		ExcelReader.Result res = ExcelReader.loadUnsent(wb);

		Metrics m = res.stats;
		assertEquals(5, m.totalRows());
		assertEquals(2, m.validPhones()); // rows 0 and 3
		assertEquals(1, m.missingPhone()); // row 1
		assertEquals(1, m.invalidPhone()); // row 2
		assertEquals(1, m.missingName()); // row 3

		List<Contact> pool = res.contacts;
		assertEquals(2, pool.size(), "Should return only unsent valid rows");
		assertEquals("9876543210", pool.get(0).phone());
		assertEquals("9123456789", pool.get(1).phone());
	}
}
