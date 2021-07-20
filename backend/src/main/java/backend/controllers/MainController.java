package backend.controllers;

import backend.entities.User;
import backend.services.UsersService;
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

        User user = usersService.findByUsername(principal.getName());
        String out = String.format("authenticated user: %s, password: %s", principal.getName(), "-");
        return out;
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}
