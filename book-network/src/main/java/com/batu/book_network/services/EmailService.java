package com.batu.book_network.services;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.batu.book_network.email.EmailTemplateName;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail (
        String to,
        String userName,
        EmailTemplateName emailTemplate,
        String confirmationUrl,
        String activationCode,
        String subject
    ) throws MessagingException
    {
        String templateName;
        
        if(emailTemplate == null){
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.name();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
            mimeMessage,
            MimeMessageHelper.MULTIPART_MODE_MIXED,
            StandardCharsets.UTF_8.name()
            );

        Map<String, Object> properites = new HashMap<>();
        properites.put("username", userName);
        properites.put("confirmationUrl", confirmationUrl);
        properites.put("activation_code", activationCode);

        // Object context is holding the properties that will be passed for the Thymeleaf template.
        Context context = new Context();
        context.setVariables(properites);

        helper.setFrom("batu_toy@hotmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        // Renders the context and bind it to the frontEnd.
        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);
        // Last stop. Wait and analyze the code for further anxious behavours.
        mailSender.send(mimeMessage);
    }   
}