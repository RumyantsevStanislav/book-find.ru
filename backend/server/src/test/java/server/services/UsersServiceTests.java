package server.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import server.entities.User;
import server.repositories.UsersRepository;
import server.utils.TestUsers;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UsersServiceTests {
    @Autowired
    private UsersService usersService;

    @MockBean
    private UsersRepository usersRepository;

    @BeforeEach
    void setUp() {
        User userFromDb = TestUsers.getUser();
        Mockito.doReturn(Optional.of(userFromDb)).when(usersRepository).findByPhone(TestUsers.USER_PHONE);
        Mockito.doReturn(Optional.of(userFromDb)).when(usersRepository).findByEmail(TestUsers.USER_EMAIL);
    }

    @Test
    public void getByPhoneOrEmailTest() {
        Optional<User> user;
        user = usersService.getUserByPhone(TestUsers.USER_PHONE);
        Assertions.assertTrue(user.isPresent());
        user = usersService.getUserByEmail(TestUsers.USER_EMAIL);
        Assertions.assertTrue(user.isPresent());
        Mockito.verify(usersRepository, Mockito.times(1)).findByPhone(ArgumentMatchers.eq(TestUsers.USER_PHONE));
        Mockito.verify(usersRepository, Mockito.times(1)).findByEmail(ArgumentMatchers.any(String.class));

        usersService.getUserByPhoneOrEmail(TestUsers.USER_PHONE);
        Mockito.verify(usersRepository, Mockito.times(2)).findByPhone(ArgumentMatchers.eq(TestUsers.USER_PHONE));
        usersService.getUserByPhoneOrEmail(TestUsers.USER_EMAIL);
        Mockito.verify(usersRepository, Mockito.times(2)).findByEmail(ArgumentMatchers.any(String.class));
    }

    @Test
    public void loadUserByUsernameTest() {
        Mockito.doReturn(Optional.empty()).when(usersRepository).findByEmail(TestUsers.NONEXISTENT_EMAIL);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> usersService.loadUserByUsername(TestUsers.NONEXISTENT_EMAIL));
        UserDetails userDetails = usersService.loadUserByUsername(TestUsers.USER_EMAIL);
        Assertions.assertEquals(userDetails.getUsername(), TestUsers.USER_EMAIL);
        Assertions.assertEquals(userDetails.getPassword(), TestUsers.USER_PASSWORD_ENCODED);
        Assertions.assertTrue(userDetails.getAuthorities().stream().allMatch(grantedAuthority -> TestUsers.getRoleUser().getAuthority().contains(grantedAuthority.getAuthority())));
    }

    //    @Test
    //    public void saveTest() {
    //        SystemUser systemUser = TestUsers.getSystemUser();
    //        Mockito.doReturn(Optional.of(TestUsers.getNewUser())).when(usersRepository).save(ArgumentMatchers.any(User.class));
    //        Mockito.doReturn(TestUsers.USER_PASSWORD_ENCODED).when(passwordEncoder.getPasswordEncoder().encode(systemUser.getPassword()));
    //        Mockito.doReturn(Role.Privilege.ROLE_USER).when(rolesService.findByName(ArgumentMatchers.any(String.class)));
    //        User savedUser = usersService.save(systemUser);
    //        Assertions.assertEquals(savedUser.getPhone(), systemUser.getPhone());
    //        Assertions.assertEquals(savedUser.getEmail(), systemUser.getEmail());
    //        Assertions.assertEquals(savedUser.getPassword(), TestUsers.USER_PASSWORD_ENCODED);
    //    }
}
