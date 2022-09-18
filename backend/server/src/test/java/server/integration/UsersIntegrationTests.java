package server.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.ActiveProfiles;
import server.entities.User;
import server.entities.dtos.ApiMessage;
import server.entities.dtos.SystemUser;
import server.repositories.UsersRepository;
import server.utils.TestUsers;

import java.util.List;

import static server.utils.Utils.mapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UsersIntegrationTests {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void successRegistrationTest() throws JsonProcessingException {
        int usersBeforeSave = usersRepository.findAll().size();
        SystemUser systemUser = TestUsers.getSystemUser();
        HttpEntity<SystemUser> request = new HttpEntity<>(systemUser);
        String response = restTemplate.postForObject("/api/v1/users/register", request, String.class);
        Assertions.assertNotNull(response);
        ApiMessage apiMessage = mapper.readValue(response, ApiMessage.class);
        Assertions.assertEquals(apiMessage.getMessage(), "Вы успешно зарегистрированы!");
        List<User> userList = usersRepository.findAll();
        Assertions.assertEquals(usersBeforeSave + 1, userList.size());
    }
}
