package com.vikrambhat.whatsapp_announcement_sender.services;

public record Metrics(int totalRows, int validPhones, int missingPhone, int invalidPhone, int missingName) {
}