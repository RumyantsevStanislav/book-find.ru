package server.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.entities.Publisher;
import server.services.PublishersService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@Api("Set of endpoints for publishers")
@AllArgsConstructor
@Slf4j
public class PublishersController {
    private PublishersService publishersService;

    @GetMapping
    @ApiOperation("Returns list of all genres in the system.")
    public List<Publisher> getAllPublishers() {
        return publishersService.findAll();
    }
}
