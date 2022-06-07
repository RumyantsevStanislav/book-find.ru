package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entities.Series;
import server.repositories.SeriesRepository;

import java.util.List;

@Service
public class SeriesService {
    private SeriesRepository seriesRepository;

    @Autowired
    public void setGenreRepository(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public List<Series> findAll() {
        return seriesRepository.findAll();
    }

    public Series findByTitle(String title) {
        return seriesRepository.findByTitle(title);
    }
}

