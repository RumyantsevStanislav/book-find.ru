package ru.bookfind.parsers.labirint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.bookfind.parsers.labirint.services.ParserService;

@SpringBootApplication
@EnableKafka
@EnableScheduling
@EnableFeignClients
public class Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args)
                .getBean(ParserService.class).save();
    }

}
