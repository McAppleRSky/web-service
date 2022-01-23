package com.example.sweater.service;

import com.example.sweater.service.helper.AppPropAndCreateUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {
    //@Autowired Почему то ,bean not wiriabeling on current spring boot version
    private final JavaMailSender mailSender;
    public MailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.app.host.transport.protocol}")
    private String tProtocol;
    @Value("${spring.app.host.name}")
    private String hostname;
    @Value("${spring.app.host.port}")
    private int port;

    public AppPropAndCreateUrl getApphostUrlPieces(){
        return new AppPropAndCreateUrl(tProtocol, hostname, port);
    }
    public void send(String emailTo, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

}
