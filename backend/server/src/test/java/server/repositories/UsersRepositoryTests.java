package server.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import server.entities.User;
import server.utils.TestUsers;

import javax.persistence.PersistenceException;
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
        user = usersRepository.findByPhone(TestUsers.USER_PHONE);
        Assertions.assertTrue(user.isPresent());
        user = usersRepository.findByPhone(TestUsers.NONEXISTENT_PHONE);
        Assertions.assertTrue(user.isEmpty());
        user = usersRepository.findByEmail(TestUsers.USER_EMAIL);
        Assertions.assertTrue(user.isPresent());
        user = usersRepository.findByEmail(TestUsers.NONEXISTENT_EMAIL);
        Assertions.assertTrue(user.isEmpty());
        Assertions.assertTrue(usersRepository.existsByPhone(TestUsers.USER_PHONE));
        Assertions.assertTrue(usersRepository.existsByEmail(TestUsers.USER_EMAIL));

        User newUser = TestUsers.getNewUser();
        Assertions.assertThrows(PersistenceException.class, () -> entityManager.persist(newUser));
    }

    @Test
    public void initDbTest() {
        List<User> userList = usersRepository.findAll();
        Assertions.assertEquals(3, userList.size());
    }
}
