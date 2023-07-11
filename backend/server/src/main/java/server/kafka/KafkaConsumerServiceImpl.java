package server.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import server.controllers.BooksController;
import server.entities.Book;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumerService {


    private BooksController booksController;

    @KafkaListener(
            topics = {"#{'${app.kafka.consumer.topic}'.split(',')}"},
            containerFactory = "kafkaListenerContainerFactory")
    public void receive(@Payload Book book) {
        WorkerThread workerThread = new WorkerThread();
        workerThread.run(book);
        //booksController.saveNewBook(book);
    }
    class WorkerThread extends Thread {

        public WorkerThread() {
            // When false, (i.e. when it's a non daemon thread),
            // the WorkerThread continues to run.
            // When true, (i.e. when it's a daemon thread),
            // the WorkerThread terminates when the main
            // thread or/and user defined thread(non daemon) terminates.
            setDaemon(true);
        }

        public void run(Book book) {
            booksController.saveNewBook(book);
        }
    }
}
