package com.example.emailnotification.listener;


import com.example.emailnotification.config.properties.EmailProperties;
import com.example.emailnotification.dto.ContractNotification;
import com.example.emailnotification.service.EmailListenerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JmsEmailListener {
    private final EmailListenerService emailService;
    private final ObjectMapper objectMapper;

    @JmsListener(destination = "email-notification")
//    public void receiveMessage(String message) {
//        log.info("Received raw message class: {}", message.getClass().getName());
//        log.info("Message content: {}", message);
//        try {
//            ContractNotification notification = objectMapper.readValue(message, ContractNotification.class);
//            log.info("Получено сообщение: {}", notification);
//            emailService.sendNotification(
//                    notification.getText(),
//                    notification.getAddressee());
//        } catch (JsonProcessingException e) {
//            log.error("Неверный формат сообщения: {}", message);
//        }
//    }

    public void receiveMessage(String raw) {
        // Разбиваем только на два сегмента: [0]=email, [1]=текст+путь
        String[] parts = raw.split("\\|", 2);

        // Просто берём первый сегмент как адресата
        String addressee = parts[0];

        // Текст письма — всё, что после первой ‘|’
        String text = parts.length > 1 ? parts[1] : "";

        // Формируем DTO и отправляем
        ContractNotification notification = new ContractNotification();
        notification.setAddressee(addressee);
        notification.setText(text);

        emailService.sendNotification(notification.getText(), notification.getAddressee());
    }
}
