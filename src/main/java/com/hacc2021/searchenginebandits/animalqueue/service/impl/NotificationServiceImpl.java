package com.hacc2021.searchenginebandits.animalqueue.service.impl;

import com.hacc2021.searchenginebandits.animalqueue.model.Owner;
import com.hacc2021.searchenginebandits.animalqueue.service.api.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmailAddress;

    @Override
    public void sendNotification(final Owner owner, final String subject, final String htmlMessage) {
        try {
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final InternetAddress to = new InternetAddress(owner.getEmailAddress(), owner.getName());
            final InternetAddress from = new InternetAddress(fromEmailAddress, "HDOA Pawsome Pickup");
            mimeMessage.addRecipient(Message.RecipientType.TO, to);
            mimeMessage.setFrom(from);
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(htmlMessage, "text/html");
            mailSender.send(mimeMessage);
        } catch (final MessagingException | UnsupportedEncodingException e) {
            log.error("Failed to send email message.", e);
        }
    }
}

