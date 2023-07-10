package ru.bookfind.parsers.labirint.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import ru.bookfind.parsers.labirint.dtos.Book;

@Service
@Slf4j
//@AllArgsConstructor
//@ConfigurationProperties(prefix = "")
public class KafkaProducerServiceImpl implements KafkaProducerService {
    @Autowired
    private KafkaTemplate<Long, Book> kafkaTemplate;

    @Value("${app.kafka.producer.topic}")
    private String topic;

    @Override
    public void send(Book book) {
        kafkaTemplate.send(topic, book);
    }
}
