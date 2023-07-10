package ru.bookfind.parsers.labirint.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bookfind.parsers.labirint.LabirintParser;
import ru.bookfind.parsers.labirint.dtos.Book;
import ru.bookfind.parsers.labirint.kafka.KafkaProducerService;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class ParserService {
    private static final String IMAGE_DESTINATION_FOLDER = "F:/BooksImages16";
    private static final String PATH = "https://www.labirint.ru/books/";

    private static final String FILENAME = "errors.txt";
    private static final String AGENT = "Chrome/81.0.4044.138";
    private KafkaProducerService kafkaProducerService;
    private RestClient restClient;

    //@Scheduled(cron = "0 0 1 ? * SAT-SUN")
    //@Scheduled(fixedDelay = 5, timeUnit = TimeUnit.HOURS)
    public void save(int from, int to) {
        saveImages(from, to);
        saveBooks(from, to);
    }

    public void saveBooks(int from, int to) {
        try (
                FileWriter fileWriter = new FileWriter(FILENAME, true);
                PrintWriter printWriter = new PrintWriter(fileWriter)
        ) {
            ExecutorService executorService = Executors.newFixedThreadPool(1);
            for (int i = from; i <= to; i++) {
//                int finalI = i;
                log.info("start = {}", System.nanoTime());
                saveBook(i, printWriter);
                log.info("finish = {}", System.nanoTime());
//            executorService.execute(() ->
//                    saveBook(finalI)
//            );
            }
            printWriter.close();
            fileWriter.close();
            executorService.shutdown();
        } catch (IOException ioException) {
            log.error("Writer IOException");
        }
    }

    private void saveImages(int from, int to) {
        for (int i = from; i <= to/*855000*/; i++) {
            String url = PATH + i;
            Connection connection = Jsoup.connect(url).userAgent("Chrome/81.0.4044.138");
            try {
                Document document = connection.execute().parse();
                Element image = Objects.requireNonNull(document.getElementById("product-image")).selectFirst("img");
                String articul = Objects.requireNonNull(document.selectFirst("div.articul")).text().substring(11);
                assert image != null;
                String strImageURL = image.attr("abs:data-src");
                downloadImage(strImageURL, articul);
            } catch (Exception e) {
                log.error("Не удалось скачать " + i);
            }
        }
    }

    private void downloadImage(String strImageURL, String articul) {
        //get file name from image path
        String strImageName = articul + ".jpg";
        //strImageURL.substring( strImageURL.lastIndexOf("/") + 1 );
        System.out.println("Saving: " + strImageName + ", from: " + strImageURL);
        try {
            //open the stream from URL
            URL urlImage = new URL(strImageURL);
            InputStream in = urlImage.openStream();
            byte[] buffer = new byte[4096];
            int n;
            OutputStream os =
                    new FileOutputStream(IMAGE_DESTINATION_FOLDER + "/" + strImageName);
            while ((n = in.read(buffer)) != -1) {
                os.write(buffer, 0, n);
            }
            os.close();
            System.out.println("Image saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBook(final int i, PrintWriter printWriter) {
        var url = PATH + i;
        Connection connection = Jsoup.connect(url).userAgent(AGENT);
        try {
            LabirintParser labirintParser = new LabirintParser(connection);
            Book book = labirintParser.getBook();
//            kafkaProducerService.send(book);
            ResponseEntity<String> response = restClient.saveBook(book);
            printWriter.println(i + " " + response.getStatusCode().is2xxSuccessful());
        } catch (IOException exception) {
            log.error("Unable to create LabirintParser {}", i);
            printWriter.println(i + " " + Boolean.FALSE);
        }
    }
}
