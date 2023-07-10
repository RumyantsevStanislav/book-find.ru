package ru.bookfind.parsers.labirint.kafka;

import ru.bookfind.parsers.labirint.dtos.Book;

public interface KafkaProducerService {
    void send(Book book);
}
