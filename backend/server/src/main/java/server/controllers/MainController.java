package server.controllers;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import server.entities.User;
import server.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {
    private UsersService usersService;

    @Autowired
    public MainController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/unsecured")
    public String unsecuredPage() {
        return "unsecured";
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }


    @GetMapping("/authenticated")
    public String authenticatedPage(Principal principal) { //Principal здесь - это UserDetails
//      Получение name не из Principal
//      Authentication a = SecurityContextHolder.getContext().getAuthentication();
//      System.out.println(Thread.currentThread().getName());

        User user = usersService.findByPhone(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(""));
        String out = String.format("authenticated user: %s, password: %s", principal.getName(), "-");
        return out;
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}
