package ru.bookfind.parsers.labirint.api;

import lombok.Data;

import java.util.List;

@Data
public class ApiError {
    private List<String> messages;
}
