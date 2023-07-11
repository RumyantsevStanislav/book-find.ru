package server.kafka;

import server.entities.Book;

public interface KafkaConsumerService {

    void receive(Book book);
}
