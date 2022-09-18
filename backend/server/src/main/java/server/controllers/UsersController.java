package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import server.configs.JwtTokenUtil;
import server.entities.User;
import server.entities.dtos.ApiMessage;
import server.entities.dtos.JwtRequest;
import server.entities.dtos.JwtResponse;
import server.entities.dtos.SystemUser;
import server.exceptions.AttributeNotValidException;
import server.exceptions.ElementAlreadyExistsException;
import server.services.UsersService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
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

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest) throws Exception {
        try {
            authenticate(authRequest.getUsername(), authRequest.getPassword());
        } catch (BadCredentialsException ex) {
            //return new ResponseEntity<>(new InfoResponse("Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = usersService.loadUserByUsername(authRequest.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
