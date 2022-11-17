package server.utils;


import server.entities.User;
import server.entities.dtos.BookDto;

import java.util.List;

// TODO: 17.11.2022 include in project 
public class MailMessageBuilder {

    public static final String SITE_NAME = "книгопоиск.ру";
    public static final String SITE_URL = "http://book-find.ru";

    private List<BookDto> books;
    private User user;

    public String buildBroadcastMail(User user, List<BookDto> books) {
        if (user == null) throw new IllegalArgumentException("User can't be null");
        if (books == null || books.isEmpty()) throw new IllegalArgumentException("Books list can't be empty");
        this.user = user;
        this.books = books;
        return headBuild() +
                bodyBuild();

    }

    private String bodyBuild() {
        return "<body>\n" +
                "<div class=\"container\"" +
                headerBuild() +
                bookListBuild() +
                footerBuild() +
                "</div>\n" +
                "</body>";
    }

    private String headBuild() {
        return "<!DOCTYPE html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "\n";
    }

    private String headerBuild() {
        return String.format(
                "<header>%n" +
                        "   <h1>Здравствуйте, %s</h1>%n" +
                        "   <p>Предлагаем ознакомиться с новинками прошедшей недели</p>%n" +
                        "</header>%n",
                user.getFirstName());
    }

    private String footerBuild() {
        return String.format(
                "<footer>" +
                        "   <p>Вы получили эту рассылку, так как подписаны на еженедельный дайджест новинок на сайте <a href=\"%s\">%s</a></p>%n" +
                        "   <p>Если Вы больше не хотите получать подобные письма, просто нажмите на ссылку <a href=\"%s\">отписаться</a></p>%n" +
                        "</footer>%n",
                SITE_URL,
                SITE_NAME,
                SITE_URL + "/user/unsubscribe");
    }

    private String bookListBuild() {
        StringBuilder bookList = new StringBuilder();
        bookList.append("<div>\n");
        for (BookDto book : books) {
            bookList.append("<div class=\"row mb-2\">\n");
            bookList.append("    <div class=\"col-md-6\">\n");
            bookList.append("      <div class=\"row no-gutters border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative\">\n");
            bookList.append("        <div class=\"col p-4 d-flex flex-column position-static\">\n");
            //bookList.append(String.format("          <strong class=\"d-inline-block mb-2 text-primary\">%s</strong>%n", book.getGenres().get(0).getName()));
            bookList.append(String.format("          <h3 class=\"mb-0\">%s</h3>%n", book.getTitle()));
            //bookList.append(String.format("          <p class=\"card-text mb-auto\">%s</p>\n", book.getDescription()));
            //bookList.append(String.format("          <a href=\"%s\" class=\"stretched-link\">Перейти на страницу книги</a>\n", SITE_URL + "/books/" + book.getId()));
            bookList.append("        </div>\n");
            bookList.append("        <div class=\"col-auto d-none d-lg-block\">\n");
            bookList.append("          <svg class=\"bd-placeholder-img\" width=\"200\" height=\"250\" xmlns=\"http://www.w3.org/2000/svg\" preserveAspectRatio=\"xMidYMid slice\" focusable=\"false\" role=\"img\" aria-label=\"Placeholder: Thumbnail\"><title>Placeholder</title><rect width=\"100%\" height=\"100%\" fill=\"#55595c\"></rect><text x=\"50%\" y=\"50%\" fill=\"#eceeef\" dy=\".3em\">Заглушка</text></svg>\n");
            bookList.append("        </div>\n");
            bookList.append("      </div>\n");
            bookList.append("    </div>\n");
            bookList.append("</div>\n");
        }
        bookList.append("</div>");
        return bookList.toString();
    }

}