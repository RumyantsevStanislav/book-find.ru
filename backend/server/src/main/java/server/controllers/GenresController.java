package server.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.entities.Genre;
import server.services.GenresService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@Api("Set of endpoints for genres")
@AllArgsConstructor
@Slf4j
public class GenresController {
    private GenresService genresService;

    @GetMapping
    @ApiOperation("Returns list of all genres in the system.")
    public List<Genre> getAllGenres() {
        return genresService.findAll();
    }
}
