package com.batu.book_network.services;

import com.batu.book_network.entites.enums.EmailTemplateName;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to,
                   String userName,
                   EmailTemplateName emailTemplate,
                   String confirmationUrl,
                   String activationCode,
                   String subject) throws MessagingException;
}
