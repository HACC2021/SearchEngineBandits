package com.hacc2021.searchenginebandits.animalqueue.service.impl;

import com.hacc2021.searchenginebandits.animalqueue.service.api.NotificationService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final SendGrid sendGrid;

    @Override
    public void sendNotification(final String toEmail, final String subject, final String htmlMessage) {
        try {
            final Email from = new Email("lehlers1@my.hpu.edu");
            final Email to = new Email(toEmail);
            final Content content = new Content("text/html", htmlMessage);
            final Mail mail = new Mail(from, subject, to, content);
            final Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        } catch (final IOException e) {
            log.error("Failed to send email message.", e);
        }
    }
}

