package ASE.deliverySpring.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Value("smtp.gmail.com")
    private String host;
    @Value("587")
    private int port;
    @Value("wangzidongchina@gmail.com")
    private String username;
    @Value("ixxgzmirxuqamfwn")
    private String password;
    @Value("smtp")
    private String protocol;
    @Value("true")
    private String auth;
    @Value("true")
    private String starttls_enable;
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls_enable);
     //   props.put("mail.debug", "true");

        return mailSender;

    }




}
