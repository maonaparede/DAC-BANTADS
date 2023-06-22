
package com.tads.dac.saga.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmail {
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(TemplateEmailInterface temp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(temp.getTo());
        message.setSubject(temp.getSubject());
        message.setText(temp.getContent());

        mailSender.send(message);
    }
    
}

