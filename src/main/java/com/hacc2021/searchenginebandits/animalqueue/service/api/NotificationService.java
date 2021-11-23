package com.hacc2021.searchenginebandits.animalqueue.service.api;

public interface NotificationService {
    void sendNotification(String toEmail, String subject, String message);
}
