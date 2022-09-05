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
import server.entities.Role;
import server.entities.User;
import server.entities.dtos.SystemUser;
import server.repositories.UsersRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService implements UserDetailsService {
    private UsersRepository usersRepository;
    private RolesService rolesService;

    private PasswordEncoder passwordEncoder;

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

    public Optional<User> getUserByPhoneOrEmail(String phoneOrEmail) {
        if (phoneOrEmail.contains("@")) {
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

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phoneOrEmail) throws UsernameNotFoundException {
        User user = getUserByPhoneOrEmail(phoneOrEmail).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", phoneOrEmail)));
        //Возвращаем UserDetails, вязв нужные поля из User
        return new org.springframework.security.core.userdetails.User(phoneOrEmail, user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    //Преобразование кастомных ролей в GrantedAuthority
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Transactional //Чтобы одновременно не создать 2 одинаковых пользователя
    public User save(SystemUser systemUser) {
        User user = new User();
        getUserByPhone(systemUser.getPhone()).ifPresent(u -> {
            throw new RuntimeException("User with " + systemUser.getPhone() + " is already exist");
        });
        getUserByEmail(systemUser.getEmail()).ifPresent(u -> {
            throw new RuntimeException("User with " + systemUser.getEmail() + " is already exist");
        });
        user.setPhone(systemUser.getPhone());
        user.setPassword(passwordEncoder.getPasswordEncoder().encode(systemUser.getPassword()));
        user.setFirstName(systemUser.getFirstName());
        user.setLastName(systemUser.getLastName());
        user.setEmail(systemUser.getEmail());
        user.setRoles(List.of(rolesService.findByName("ROLE_CUSTOMER")));
        return usersRepository.save(user);
    }
}
