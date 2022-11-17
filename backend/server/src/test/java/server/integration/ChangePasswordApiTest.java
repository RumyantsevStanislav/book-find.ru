// TODO: 17.11.2022 figure out
//package server.integration;
//
//import io.restassured.RestAssured;
//import io.restassured.authentication.FormAuthConfig;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import server.configs.PasswordEncoder;
//import server.entities.User;
//import server.repositories.UsersRepository;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
////@ContextConfiguration(
////        classes = {ConfigTest.class, PersistenceJPAConfig.class},
////        loader = AnnotationConfigContextLoader.class)
//public class ChangePasswordApiTest {
//    private final String URL_PREFIX = "http://localhost:8080/";
//    private final String URL = URL_PREFIX + "/user/updatePassword";
//    FormAuthConfig formConfig = new FormAuthConfig(
//            URL_PREFIX + "/login", "username", "password");
//    @Autowired
//    private UsersRepository usersRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @BeforeEach
//    public void init() {
//        User user = usersRepository.findByEmail("test@test.com").get();
//        if (user == null) {
//            user = new User();
//            user.setFirstName("Test");
//            user.setLastName("Test");
//            user.setPassword(passwordEncoder.getPasswordEncoder().encode("test"));
//            user.setEmail("test@test.com");
//            user.setEnabled(true);
//            usersRepository.save(user);
//        } else {
//            user.setPassword(passwordEncoder.getPasswordEncoder().encode("test"));
//            usersRepository.save(user);
//        }
//    }
//
//    @Test
//    public void givenWrongOldPassword_whenChangingPassword_thenBadRequest() {
//        RequestSpecification request = RestAssured.given().auth()
//                .form("test@test.com", "test", formConfig);
//
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("oldpassword", "abc");
//        params.put("password", "newtest");
//
//        Response response = request.with().params(params).post(URL);
//
//        assertEquals(400, response.statusCode());
//        assertTrue(response.body().asString().contains("Invalid Old Password"));
//    }
//
//    @Test
//    public void givenNotAuthenticatedUser_whenChangingPassword_thenRedirect() {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("oldpassword", "abc");
//        params.put("password", "xyz");
//
//        Response response = RestAssured.with().params(params).post(URL);
//
//        assertEquals(302, response.statusCode());
//        assertFalse(response.body().asString().contains("Password updated successfully"));
//    }
//}