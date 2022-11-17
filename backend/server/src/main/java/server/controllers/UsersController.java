package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import server.configs.JwtTokenUtil;
import server.entities.PasswordResetToken;
import server.entities.User;
import server.entities.VerificationToken;
import server.entities.dtos.*;
import server.exceptions.AttributeNotValidException;
import server.exceptions.ElementAlreadyExistsException;
import server.exceptions.GenericResponse;
import server.services.UsersService;
import server.utils.validation.Marker;
import server.verification.OnRegistrationCompleteEvent;
import server.verification.OnResendTokenEvent;
import server.verification.OnResetPasswordEvent;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@Validated
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
    @Validated({Marker.OnCreate.class})
    public ResponseEntity<ApiMessage> register(@Valid @RequestBody SystemUser systemUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new AttributeNotValidException("Ошибка валидации", bindingResult);
        }
        usersService.getUserByPhoneOrEmail(systemUser.getPhoneOrEmail()).ifPresent(u -> {
            throw new ElementAlreadyExistsException("Пользователь уже существует.");
        });
        User registeredUser = usersService.save(systemUser);
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, request.getLocale(), appUrl));
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

    @GetMapping("/registrationConfirm")
    public ResponseEntity<ApiMessage> confirmRegistration(WebRequest request, @RequestParam("token") String token) {
        Locale locale = request.getLocale();
        VerificationToken verificationToken = usersService.getVerificationToken(token);
        if (verificationToken == null) {
            String messageValue = messages.getMessage("auth.message.invalidToken", null, locale);
            return new ResponseEntity<>(new ApiMessage(messageValue), HttpStatus.NOT_FOUND);
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            return new ResponseEntity<>(new ApiMessage(messageValue), HttpStatus.UNAUTHORIZED);
        }
        usersService.activateUser(user);
        String messageValue = messages.getMessage("auth.message.success", null, locale);
        return new ResponseEntity<>(new ApiMessage(messageValue), HttpStatus.ACCEPTED);
    }

    @GetMapping("/resendRegistrationToken")
    public GenericResponse resendRegistrationToken(HttpServletRequest request, @RequestParam("token") String existingToken) {
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnResendTokenEvent(existingToken, request.getLocale(), appUrl));
        return new GenericResponse(messages.getMessage("message.resendToken", null, request.getLocale()));
    }

    @PostMapping("/resetPassword")
    public GenericResponse resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnResetPasswordEvent(userEmail, request.getLocale(), appUrl));
        return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    }

    @GetMapping("/changePassword")
    public ResponseEntity<ApiMessage> showChangePasswordPage(Locale locale,/*is it possible?*/ @RequestParam("token") String token) {
        //Locale locale = request.getLocale();
        PasswordResetToken passwordResetToken = usersService.getPasswordResetToken(token);
        if (passwordResetToken == null) {
            String messageValue = messages.getMessage("auth.message.invalidToken", null, locale);
            return new ResponseEntity<>(new ApiMessage(messageValue), HttpStatus.NOT_FOUND);
        }
        User user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((passwordResetToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            return new ResponseEntity<>(new ApiMessage(messageValue), HttpStatus.UNAUTHORIZED);
        }
        //String result = validatePasswordResetToken(token); //jwtTokenUtil?
        //        if (result != null) {
        //            String message = messages.getMessage("auth.message." + result, null, locale);
        //            return "redirect:/login.html?lang=" + locale.getLanguage() + "&message=" + message;
        //        } else {
        //            return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
        //        }
        String messageValue = messages.getMessage("auth.message.success", null, locale);
        return new ResponseEntity<>(new ApiMessage(messageValue /*token?*/), HttpStatus.ACCEPTED);
    }

    @PostMapping("/savePassword")
    public GenericResponse savePassword(final Locale locale, @Valid PasswordDto passwordDto) {
        String result = validatePasswordResetToken(passwordDto.getToken());
        if (result != null) {
            return new GenericResponse(messages.getMessage("auth.message." + result, null, locale));
        }
        User user = usersService.getUserByPasswordResetToken(passwordDto.getToken());
        if (user != null) {
            usersService.changeUserPassword(user, passwordDto.getNewPassword());
            return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
        } else {
            return new GenericResponse(messages.getMessage("auth.message.invalid", null, locale));
        }
    }

    @PostMapping("/updatePassword")
    @PreAuthorize("hasRole('ROLE_USER')")
    //@PreAuthorize annotation, since it should only accessible to logged in users.
    public GenericResponse changeUserPassword(Locale locale, @RequestParam("password") String password, @RequestParam("oldpassword") String oldPassword) {
        User user = usersService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); //WTF?

        if (!usersService.isValidOldPassword(user, oldPassword)) {
            //throw new InvalidOldPasswordException();
            return new GenericResponse("Invalid old password");
        }
        usersService.changeUserPassword(user, password);
        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }

    public String validatePasswordResetToken(String token) {
        //        final PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token);
        //
        //        return !isTokenFound(passToken) ? "invalidToken"
        //                : isTokenExpired(passToken) ? "expired"
        //                : null;
        return "";
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }


    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
