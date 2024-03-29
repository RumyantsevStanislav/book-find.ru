package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.entities.Author;
import server.repositories.AuthorsRepository;

import java.util.List;

@Service
public class AuthorsService {
    private AuthorsRepository authorsRepository;

    @Autowired
    public void setAuthorRepository(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public List<Author> findAll() {
        return authorsRepository.findAll();
    }

    public Author findByNameAndRole(String name, String role) {
        return authorsRepository.findByNameAndRole(name, role);
    }
}

