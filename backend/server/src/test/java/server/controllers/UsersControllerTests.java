package server.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import server.configs.JwtTokenUtil;
import server.entities.User;
import server.entities.dtos.SystemUser;
import server.services.UsersService;
import server.utils.TestUsers;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static server.utils.Utils.mapper;

@WebMvcTest(controllers = {UsersController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("unsecured")
public class UsersControllerTests {
    SystemUser systemUser = TestUsers.getSystemUser();
    User user = TestUsers.getNewUser();
    String systemUserJson = mapper.writeValueAsString(systemUser);

    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private UsersService usersService;
    @Autowired
    private MockMvc mvc;

    public UsersControllerTests() throws JsonProcessingException {
    }

    @Test
    @DisplayName("Success register")
    public void successRegistrationTest() throws Exception {
        given(usersService.save(systemUser)).willReturn(user);
        mvc.perform(post("/api/v1/users/register").content(systemUserJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Вы успешно зарегистрированы!")))
                .andReturn();
    }

    @Test
    @DisplayName("User already exist")
    public void failRegistrationTest() throws Exception {
        Mockito.doReturn(Optional.of(user)).when(usersService).getUserByPhoneOrEmail(systemUser.getPhoneOrEmail());
        mvc.perform(post("/api/v1/users/register").content(systemUserJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages", hasSize(1)))
                .andExpect(jsonPath("$.messages[0]", is("Пользователь уже существует.")))
                //.andExpect(content().json("{\"errors\":[\"Task name must not be blank!\"],\"errorMessage\":\"Validation failed. 1 error(s)\"}"));
                .andReturn();
    }

}
