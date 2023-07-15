package ru.bookfind.parsers.labirint.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Data
public class Book {

    private Long labirintId;

    private String title;

    private String description;

    private Integer price;

    private Integer pages;

    private Integer year;

    private Float estimation;

    private Integer estimationsCount;

    private String isbn;

    private Set<Isbn> isbns = new HashSet<>();

    private Status status;

    private Set<Category> categories;

    private Set<Author> authors;

    private Publisher publisher;

    private Genre genre;

    private Series series;

    private Cover cover;

    @AllArgsConstructor
    @Getter
    public enum Status {
        ACTIVE, HIDDEN;
    }
}