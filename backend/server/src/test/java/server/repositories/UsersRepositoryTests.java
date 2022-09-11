package server.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import server.entities.Role;
import server.entities.User;

import javax.persistence.PersistenceException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class UsersRepositoryTests {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void usersRepositoryTest() {
        Optional<User> user;
        user = usersRepository.findByPhone("12345678");
        Assertions.assertTrue(user.isPresent());
        user = usersRepository.findByPhone("87654321");
        Assertions.assertTrue(user.isEmpty());
        user = usersRepository.findByEmail("admin@gmail.com");
        Assertions.assertTrue(user.isPresent());
        user = usersRepository.findByEmail("qwerty@gmail.com");
        Assertions.assertTrue(user.isEmpty());
        Assertions.assertTrue(usersRepository.existsByPhone("12345678"));
        Assertions.assertTrue(usersRepository.existsByEmail("admin@gmail.com"));

        User newUser = new User("87654321", "qwerty@bk.ru", "$2a$10$FlpY8S3SIerY1HBe/1zuTevGX1oR1gZ8YMe/4F4qBTJll25ArQDx6", "name", "surname", OffsetDateTime.now(), List.of(new Role(Role.Privilege.ROLE_USER)));
        Assertions.assertThrows(PersistenceException.class, () -> entityManager.persist(newUser));
    }

    @Test
    public void initDbTest() {
        List<User> userList = usersRepository.findAll();
        Assertions.assertEquals(1, userList.size());
    }
}
