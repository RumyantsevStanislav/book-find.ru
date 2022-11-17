package server.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.configs.PasswordEncoder;
import server.entities.PasswordResetToken;
import server.entities.Role;
import server.entities.User;
import server.entities.VerificationToken;
import server.entities.dtos.SystemUser;
import server.repositories.PasswordResetTokenRepository;
import server.repositories.UsersRepository;
import server.repositories.VerificationTokenRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService implements UserDetailsService { //см. Борисов UsersServiceImpl
    private UsersRepository usersRepository;
    private RolesService rolesService;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepository verificationTokenRepository;
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setRolesService(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @Autowired
    public void setVerificationTokenRepository(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Autowired
    public void setPasswordResetTokenRepository(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public Optional<User> getUserByPhoneOrEmail(String phoneOrEmail) {
        if (isEmail(phoneOrEmail)) {
            return usersRepository.findByEmail(phoneOrEmail);
        } else {
            return usersRepository.findByPhone(phoneOrEmail);
        }
    }

    public Optional<User> getUserByPhone(String phone) {
        return usersRepository.findByPhone(phone);
    }

    public Optional<User> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    //@Override
    @Transactional
    public UserDetails loadUserByUsername(String phoneOrEmail) throws UsernameNotFoundException {
        User user = getUserByPhoneOrEmail(phoneOrEmail).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", phoneOrEmail)));
        //Возвращаем UserDetails, вязв нужные поля из User
        return new org.springframework.security.core.userdetails.User(phoneOrEmail, user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    //Преобразование кастомных ролей в GrantedAuthority
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getPrivilege().name())).collect(Collectors.toList());
    }

    @Transactional //Чтобы одновременно не создать 2 одинаковых пользователя
    public User save(SystemUser systemUser) {
        User user = new User();
        String phoneOrEmail = systemUser.getPhoneOrEmail();
        if (isEmail(phoneOrEmail)) {
            user.setEmail(phoneOrEmail);
        } else {
            user.setPhone(phoneOrEmail);
        }
        user.setPassword(passwordEncoder.getPasswordEncoder().encode(systemUser.getPassword()));
        user.setRoles(List.of(rolesService.getByPrivilege(Role.Privilege.ROLE_USER)));
        return usersRepository.save(user);
    }

    public VerificationToken createVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public VerificationToken generateNewVerificationToken(String existingToken) {
        VerificationToken existingVerificationToken = getVerificationToken(existingToken);
        VerificationToken newVerificationToken = new VerificationToken(existingVerificationToken.getUser());
        existingVerificationToken.setToken(newVerificationToken.getToken());
        existingVerificationToken.setExpiryDate(existingVerificationToken.getExpiryDate());
        verificationTokenRepository.save(existingVerificationToken);
        return existingVerificationToken;
    }

    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Токен не найден!"));
    }

    public void activateUser(User user) {
        User existingUser = getUserByEmail(user.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не существует"));
        existingUser.setEnabled(true);
        usersRepository.save(existingUser);
    }

    public User getUser(String token) {
        return getVerificationToken(token).getUser();
    }

    public PasswordResetToken createPasswordResetToken(User user) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user);
        passwordResetTokenRepository.save(passwordResetToken);
        return passwordResetToken;
    }

    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Токен не найден!"));
    }

    public User getUserByPasswordResetToken(String token) {
        return getPasswordResetToken(token).getUser();
    }

    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.getPasswordEncoder().encode(password));
        usersRepository.save(user);
    }

    public boolean isValidOldPassword(User user, String oldPassword) {
        return user.getPassword().equals(passwordEncoder.getPasswordEncoder().encode(oldPassword));
    }


    private boolean isEmail(String phoneOrEmail) {
        return phoneOrEmail.contains("@");
    }
}
