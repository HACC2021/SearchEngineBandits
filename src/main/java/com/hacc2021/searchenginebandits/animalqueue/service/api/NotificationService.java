package com.hacc2021.searchenginebandits.animalqueue.service.api;

import com.hacc2021.searchenginebandits.animalqueue.model.Owner;

public interface NotificationService {
    void sendNotification(Owner owner, String subject, String message);
}
