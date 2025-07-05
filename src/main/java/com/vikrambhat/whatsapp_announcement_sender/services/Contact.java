package com.vikrambhat.whatsapp_announcement_sender.services;

import org.apache.poi.ss.usermodel.Row;

public record Contact(String name, String phone, Row row) {
}