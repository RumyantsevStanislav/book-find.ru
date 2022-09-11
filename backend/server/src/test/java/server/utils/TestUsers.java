package server.utils;

import server.entities.Role;
import server.entities.User;
import server.entities.dtos.SystemUser;

import java.util.Collections;

public class TestUsers {

    public static final String
            ADMIN_PHONE = "11111111",
            ADMIN_PASSWORD_ENCODED = "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
            ADMIN_PASSWORD_DECODED = "1234",
            MANAGER_PHONE = "22222222",
            MANAGER_PASSWORD_ENCODED = "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
            MANAGER_PASSWORD_DECODED = "1234",
            USER_PHONE = "33333333",
            USER_EMAIL = "user@gmail.com",
            USER_PASSWORD_ENCODED = "$2a$10$ts2FXg1jVvuuEIJqIItTB.Ra1ZklHcSYyrnt3AkGVJkekFUjWcu9K",
            USER_PASSWORD_DECODED = "1234",
            NONEXISTENT_PHONE = "00000000",
            NONEXISTENT_EMAIL = "nonexistent@gmail.com",
            INCORRECT_PASSWORD = "12345",
            NEW_USER_PHONE = "44444444",
            NEW_USER_EMAIL = "new_user@gmail.com",
            NEW_USER_PASSWORD_DECODED = "1234";


    public static User getAdmin() {
        User admin = new User();
        admin.setId(1L);
        admin.setPhone(ADMIN_PHONE);
        admin.setPassword(ADMIN_PASSWORD_ENCODED);
        Role role = getRoleAdmin();
        admin.setRoles(Collections.singletonList(role));
        return admin;
    }

    public static User getManager() {
        User manager = new User();
        manager.setId(2L);
        manager.setPhone(MANAGER_PHONE);
        manager.setPassword(MANAGER_PASSWORD_ENCODED);
        Role role = getRoleManager();
        manager.setRoles(Collections.singletonList(role));
        return manager;
    }

    public static User getUser() {
        User user = new User();
        user.setId(3L);
        user.setPhone(USER_PHONE);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD_ENCODED);
        Role role = getRoleUser();
        user.setRoles(Collections.singletonList(role));
        return user;
    }

    public static User getNewUser() {
        User user = new User();
        user.setPhone(NEW_USER_PHONE);
        user.setEmail(NEW_USER_EMAIL);
        user.setPassword(NEW_USER_PASSWORD_DECODED);
        Role role = getRoleUser();
        user.setRoles(Collections.singletonList(role));
        return user;
    }

    public static SystemUser getSystemUser() {
        SystemUser systemUser = new SystemUser();
        systemUser.setPhone(NEW_USER_PHONE);
        systemUser.setEmail(NEW_USER_EMAIL);
        systemUser.setPassword(NEW_USER_PASSWORD_DECODED);
        systemUser.setMatchingPassword(NEW_USER_PASSWORD_DECODED);
        return systemUser;
    }

    public static Role getRoleAdmin() {
        Role role = new Role();
        role.setPrivilege(Role.Privilege.ROLE_ADMIN);
        return role;
    }

    public static Role getRoleManager() {
        Role role = new Role();
        role.setPrivilege(Role.Privilege.ROLE_MANAGER);
        return role;
    }

    public static Role getRoleUser() {
        Role role = new Role();
        role.setPrivilege(Role.Privilege.ROLE_USER);
        return role;
    }
}
