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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import server.configs.JwtRequestFilter;
import server.entities.User;
import server.entities.dtos.SystemUser;
import server.services.UsersService;
import server.utils.TestUsers;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static server.utils.Utils.mapper;

@WebMvcTest(controllers = {UsersController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class UsersControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsersService usersService;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    SystemUser systemUser = TestUsers.getSystemUser();
    User user = TestUsers.getNewUser();
    String systemUserJson = mapper.writeValueAsString(systemUser);

    public UsersControllerTests() throws JsonProcessingException {
    }

    @Test
    @DisplayName("Success register")
    public void successRegisterTest() throws Exception {
        given(usersService.save(systemUser)).willReturn(user);
        mvc.perform(post("/api/v1/users/register").content(systemUserJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Вы успешно зарегистрированы!")))
                .andReturn();
    }

    @Test
    @DisplayName("User already exist")
    public void failRegisterTest() throws Exception {
        Mockito.doReturn(Optional.of(user)).when(usersService).getUserByPhone(systemUser.getPhone());
        mvc.perform(post("/api/v1/users/register").content(systemUserJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages", hasSize(1)))
                .andExpect(jsonPath("$.messages[0]", is("Пользователь уже существует.")))
                .andReturn();
    }

}
