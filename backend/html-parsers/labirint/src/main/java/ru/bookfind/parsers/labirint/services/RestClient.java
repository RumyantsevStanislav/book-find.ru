package ru.bookfind.parsers.labirint.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.bookfind.parsers.labirint.configs.FeignClientConfig;
import server.entities.Book;

@FeignClient(value = "books"/*, configuration = FeignClientConfig.class*/)
public interface RestClient {
    @PostMapping("/")
    ResponseEntity<String> saveBook(Book book);

//    @RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
//    Post getPostById(@PathVariable("postId") Long postId);

}
