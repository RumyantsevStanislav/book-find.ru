package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entities.Genre;
import server.repositories.GenresRepository;

import java.util.List;

@Service
public class GenresService {
    private GenresRepository genresRepository;

    @Autowired
    public void setGenreRepository(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    public List<Genre> findAll() {
        return genresRepository.findAll();
    }

    public Genre findByPath(String path) {
        return genresRepository.findByPath(path);
    }
}

