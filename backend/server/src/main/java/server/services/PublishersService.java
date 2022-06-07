package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entities.Publisher;
import server.repositories.PublishersRepository;

import java.util.List;

@Service
public class PublishersService {
    private PublishersRepository publishersRepository;

    @Autowired
    public void setPublishersRepository(PublishersRepository publishersRepository) {
        this.publishersRepository = publishersRepository;
    }

    public List<Publisher> findAll() {
        return publishersRepository.findAll();
    }

    public Publisher findByTitle(String title) {
        return publishersRepository.findByTitle(title);
    }
}

