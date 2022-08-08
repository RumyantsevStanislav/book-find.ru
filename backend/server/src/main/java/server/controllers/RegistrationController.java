package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import server.entities.User;
import server.entities.dtos.SystemUser;
import server.exceptions.ReqErrorResponse;
import server.services.UsersService;

import java.util.Optional;

@Controller
public class RegistrationController {
    private UsersService usersService;

    @Autowired
    public void setUserService(UsersService usersService) {
        this.usersService = usersService;
    }

    @InitBinder //Обрубает пробелы в полях формы на уровне контроллера
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/register")
    public String showMyLoginPage(Model model) {
        model.addAttribute("systemUser", new SystemUser());
        return "registration-form";
    }

    @PostMapping("/register/process")
    public ResponseEntity<?> processRegistrationForm(@ModelAttribute("systemUser") @Validated SystemUser systemUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new ReqErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Binding result has errors"), HttpStatus.NOT_ACCEPTABLE);
        }
        Optional<User> existing = usersService.findByPhone(systemUser.getPhone());
        if (existing.isPresent()) {
            model.addAttribute("registrationError", "User with phone number: [" + systemUser.getPhone() + "] is already exist");
            systemUser.setPhone(null);
            model.addAttribute("systemUser", systemUser); // Заполнение ранее введенных полей если возникла ошибка при регистрации.
            return new ResponseEntity<>(new ReqErrorResponse(HttpStatus.CONFLICT.value(), "Already exist"), HttpStatus.CONFLICT);
        }
        usersService.save(systemUser);
        return ResponseEntity.ok(systemUser);
    }
}
