package ru.bookfind.parsers.labirint.kafka;

import server.entities.Book;

public interface KafkaProducerService {
    void send(Book book);
}
