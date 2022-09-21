package server.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import server.repositories.BooksRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT /*classes = Book.class* Инициализирует только этот бин в контексте. Через new плохо, т.к. есть PostConstruct и т.д.*/)
@ActiveProfiles("test")
public class BooksIntegrationTest {
    @Autowired
    BooksRepository booksRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void fullRestTest() {
        String response = restTemplate.getForObject("/api/v1/books?min_price=0&s=1", String.class);
        //TODO cast response to Page<BoorDtoImpl> and assert fields.
        Assertions.assertNotNull(response);
    }
}