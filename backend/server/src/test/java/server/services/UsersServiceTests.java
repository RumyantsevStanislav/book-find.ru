package server.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import server.configs.PasswordEncoder;
import server.entities.User;
import server.entities.dtos.SystemUser;
import server.repositories.UsersRepository;
import server.utils.TestUsers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTests {
    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private RolesService rolesService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        User userFromDb = TestUsers.getUser();
        Mockito.lenient().doReturn(Optional.of(userFromDb)).when(usersRepository).findByEmail(TestUsers.USER_EMAIL);
        Mockito.lenient().doReturn(Optional.of(userFromDb)).when(usersRepository).findByPhone(TestUsers.USER_PHONE);
    }

    @Test
    @DisplayName("Get user by phone or email is success")
    public void getByPhoneOrEmailTest() {
        Optional<User> user;
        user = usersService.getUserByPhone(TestUsers.USER_PHONE);
        Assertions.assertTrue(user.isPresent());
        user = usersService.getUserByEmail(TestUsers.USER_EMAIL);
        Assertions.assertTrue(user.isPresent());
        Mockito.verify(usersRepository, Mockito.times(1)).findByPhone(eq(TestUsers.USER_PHONE));
        Mockito.verify(usersRepository, Mockito.times(1)).findByEmail(any(String.class));

        usersService.getUserByPhoneOrEmail(TestUsers.USER_PHONE);
        Mockito.verify(usersRepository, Mockito.times(2)).findByPhone(eq(TestUsers.USER_PHONE));
        usersService.getUserByPhoneOrEmail(TestUsers.USER_EMAIL);
        Mockito.verify(usersRepository, Mockito.times(2)).findByEmail(any(String.class));
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

    @Test
    public void saveTest() {
        SystemUser systemUser = TestUsers.getSystemUser();
        Mockito.doReturn(TestUsers.getUser()).when(usersRepository).save(any(User.class));
        Mockito.doReturn(bCryptPasswordEncoder).when(passwordEncoder).getPasswordEncoder();
        Mockito.doReturn(TestUsers.USER_PASSWORD_ENCODED).when(bCryptPasswordEncoder).encode(systemUser.getPassword());
        Mockito.doReturn(TestUsers.getRoleUser()).when(rolesService).findByName(any(String.class));
        User savedUser = usersService.save(systemUser);
        Assertions.assertEquals(savedUser.getPassword(), TestUsers.USER_PASSWORD_ENCODED);
    }
}
