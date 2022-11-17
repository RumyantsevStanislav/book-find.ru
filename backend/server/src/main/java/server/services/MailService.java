package server.services;

import server.entities.User;
import server.entities.dtos.BookDto;
import server.entities.dtos.SubscriptionDto;

import java.util.List;

// TODO: 17.11.2022 include in project
public interface MailService {
    void sendLastWeekBooksMail(SubscriptionDto subscriptionDto);

    void sendBroadcastMail(List<User> users, List<BookDto> books);
}