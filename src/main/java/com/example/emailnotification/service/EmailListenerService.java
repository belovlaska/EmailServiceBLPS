package com.example.emailnotification.service;

import com.example.emailnotification.config.properties.EmailProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailListenerService {
    private final JavaMailSender javaMailSender;
    private final EmailProperties emailProperties;

    public void sendNotification(String text, String addressee) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(emailProperties.getFrom());
            mailMessage.setTo(addressee);
            mailMessage.setText(String.format("Вам пришло новое сообщение: %s", text));
            mailMessage.setSubject(emailProperties.getSubject());

            javaMailSender.send(mailMessage);
            log.info("Email sent to {}", addressee);
        }
        catch (Exception e) {
            log.error("Failed to send email", e);
        }
    }
}
