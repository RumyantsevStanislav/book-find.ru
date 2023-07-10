package ru.bookfind.parsers.labirint.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bookfind.parsers.labirint.services.ParserService;

@RestController
@AllArgsConstructor
public class ParserController {

    private ParserService parserService;

    @GetMapping(value = "/saveBooks/from/{from}/to/{to}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void saveBooks(@PathVariable int from, @PathVariable int to) {
         new Thread(() -> {
             parserService.saveBooks(from, to);
         }).start();
    }
}
