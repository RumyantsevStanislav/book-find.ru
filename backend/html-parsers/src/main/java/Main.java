import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import server.entities.Book;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String IMAGE_DESTINATION_FOLDER = "F:/BooksImages16";
    private static final String PATH = "https://www.labirint.ru/books/";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger();

    private static final PrintWriter writer;

    static {
        try {
            writer = new PrintWriter("errors.txt", StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Main() throws IOException {
    }

    public static void main(String[] args) {
        //saveImages();
        saveBooks();
    }

    private static void saveBooks() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 1; i <= 20; i++) {
            int finalI = i;
            executorService.execute(() ->
                    saveBook(finalI)
            );
        }
        executorService.shutdown();
    }

    private static void saveImages() {
        for (int i = 195685; i <= 195685/*855000*/; i++) {
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
                logger.error("Не удалось скачать " + i);
            }
        }
    }

    private static void downloadImage(String strImageURL, String articul) {
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

    private static void saveBook(final int i) {
        var url = "https://www.labirint.ru/books/" + i;
        Connection connection = Jsoup.connect(url).userAgent("Chrome/81.0.4044.138");
        try {
            LabirintParser labirintParser = new LabirintParser(connection);
            Book book = labirintParser.getBook();
            final Content postResult = Request.Post("http://localhost:8189/book-find/api/v1/books")
                    .bodyString(objectMapper.writeValueAsString(book), ContentType.APPLICATION_JSON)
                    .execute().returnContent();
            //System.out.println(postResult.asString());
        } catch (Exception exception) {
            logger.error("Unable to create LabirintParser {}", i);
            writer.println(i);
        }
    }
}
