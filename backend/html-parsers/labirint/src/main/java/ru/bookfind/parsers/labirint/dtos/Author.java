package ru.bookfind.parsers.labirint.dtos;

import lombok.Data;

@Data
public class Author {

    private Long labirintId;

    private String name;

    private String role;
}