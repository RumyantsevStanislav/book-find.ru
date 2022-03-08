import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Main {
    private static final String IMAGE_DESTINATION_FOLDER = "F:/BooksImages8";
    private static final String PATH = "https://www.labirint.ru/books/";

    public static void main(String[] args) throws IOException {
        for (int i = 435623; i <= 450000; i++) {
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
                //System.out.println("Не удалось скачать " + i);
            }
        }

//        Elements author = document.select("div.authors > a");
//        String authorName =author.text();
        //> вложенные теги
        //Elements titleElem = document.select("head > title");
        //System.out.println(author);
//        Book book = new Book();
//        book.setTitle("title");
//        //book.setCategories("category");
//        book.setDescription("description");
//        book.setPrice(123F);


//        final Content getResult = Request.Get("http://jsonplaceholder.typicode.com/posts?_limit=10")
//                .execute().returnContent();
//        System.out.println(getResult.asString());

        final Collection<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("title", "foo"));
        params.add(new BasicNameValuePair("body", "bar"));
        params.add(new BasicNameValuePair("userId", "1"));

//        final Content postResultForm = Request.Post("http://jsonplaceholder.typicode.com/posts")
//                .bodyForm(params, Charset.defaultCharset())
//                .execute().returnContent();
//        System.out.println(postResultForm.asString());

//        final Content postResult = Request.Post("http://localhost:8189/book-find/api/v1/books")
//                .bodyString(objectMapper.writeValueAsString(book), ContentType.APPLICATION_JSON)
//                .execute().returnContent();
//        System.out.println(postResult.asString());

//        Elements paragraphs = document.getElementsByTag("p");
//        for (Element paragraph : paragraphs) {
//            System.out.println(paragraph.text());
//        }
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
            int n = -1;

            OutputStream os =
                    new FileOutputStream(IMAGE_DESTINATION_FOLDER + "/" + strImageName);

            //write bytes to the output stream
            while ((n = in.read(buffer)) != -1) {
                os.write(buffer, 0, n);
            }

            //close the stream
            os.close();

            //System.out.println("Image saved");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
