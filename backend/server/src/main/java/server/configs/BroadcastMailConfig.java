package server.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

// TODO: 17.11.2022 use for sending subscription messages
@Configuration
@EnableAsync
@EnableScheduling
@PropertySource("classpath:broadcastmail.properties")
public class BroadcastMailConfig {

    @Value("${mail.broadcast.host}")
    private String host;

    @Value("${mail.broadcast.port}")
    private int port;

    @Value("${mail.broadcast.username}")
    private String username;

    @Value("${mail.broadcast.password}")
    private String password;
    // TODO: 17.11.2022  use Qualifier or Priority

    //    @Bean
    //    public JavaMailSender javaMailSender() {
    //        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    //        mailSender.setHost(host);
    //        mailSender.setPort(port);
    //
    //        mailSender.setUsername(username);
    //        mailSender.setPassword(password);
    //
    //        Properties props = mailSender.getJavaMailProperties();
    //        props.put("mail.transport.protocol", "smtp");
    //        props.put("mail.smtp.auth", "true");
    //        props.put("mail.smtp.starttls.enable", "true");
    //        props.put("mail.debug", "true");
    //        props.put("mail.from", username);
    //
    //        return mailSender;
    //    }
}
