package server.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.entities.Author;
import server.services.AuthorsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@Api("Set of endpoints for authors")
@AllArgsConstructor
@Slf4j
public class AuthorsController {
    private AuthorsService authorsService;

    @GetMapping
    @ApiOperation("Returns list of all authors in the system.")
    public List<Author> getAllAuthors() {
        return authorsService.findAll();
    }
}
