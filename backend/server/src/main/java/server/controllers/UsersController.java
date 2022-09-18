package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import server.entities.User;
import server.entities.dtos.ApiMessage;
import server.entities.dtos.SystemUser;
import server.exceptions.AttributeNotValidException;
import server.exceptions.ElementAlreadyExistsException;
import server.services.UsersService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
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

    @PostMapping("/register")
    public ResponseEntity<ApiMessage> register(@Valid @RequestBody SystemUser systemUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new AttributeNotValidException("Ошибка валидации", bindingResult);
        }
        Optional<User> existingByPhone = usersService.getUserByPhone(systemUser.getPhone());
        Optional<User> existingByEmail = usersService.getUserByEmail(systemUser.getEmail());
        if (existingByPhone.isPresent() || existingByEmail.isPresent()) {
            throw new ElementAlreadyExistsException("Пользователь уже существует.");
        }
        usersService.save(systemUser);
        return new ResponseEntity<>(new ApiMessage("Вы успешно зарегистрированы!"), HttpStatus.CREATED);
    }
}
