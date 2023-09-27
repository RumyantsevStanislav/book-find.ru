package server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import server.entities.User;
import server.entities.dtos.BookDto;
import server.entities.dtos.SubscriptionDto;
import server.utils.MailMessageBuilder;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

// TODO: 17.11.2022 include in project
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private static final String BROADCAST_TITLE = "Новинки недели";

    private final MailMessageBuilder messageBuilder = new MailMessageBuilder();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final JavaMailSender javaMailSender;
    private final UsersService userService;
    private final BooksService bookService;


    public void sendLastWeekBooksMail(SubscriptionDto subscriptionDto) {
        //        List<User> users = subscriptionDto.getUsersId().stream()
        //                .map(userService::findByUserId)
        //                .collect(Collectors.toList());
        //        List<BookDto> books = subscriptionDto.getBooksId().stream()
        //                .map(bookService::findById)
        //                .collect(Collectors.toList());
        //        sendBroadcastMail(users, books);
    }

    public void sendBroadcastMail(List<User> users, List<BookDto> books) {
        for (User user : users) {
            sendMail(user.getEmail(), BROADCAST_TITLE, messageBuilder.buildBroadcastMail(user, books));
        }
    }

    private void sendMail(String email, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        try {
            helper.setTo(email);
            helper.setText(text, true);
            helper.setSubject(subject);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            executorService.submit(() -> javaMailSender.send(message));
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}