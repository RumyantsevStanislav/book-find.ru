package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UsersController {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UsersService usersService;
    private final ApplicationEventPublisher eventPublisher;
    private final MessageSource messages;

    @Autowired
    public UsersController(UsersService usersService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, ApplicationEventPublisher eventPublisher, MessageSource messages) {
        this.usersService = usersService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.eventPublisher = eventPublisher;
        this.messages = messages;
    }

    /**
     * Cutting spaces in form fields on controller level.
     *
     * @param dataBinder
     */
    @InitBinder //
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/register")
    @Validated({Marker.OnCreate.class})
    public ResponseEntity<ApiMessage> register(@Valid @RequestBody SystemUser systemUser, /*BindingResult bindingResult,*/ HttpServletRequest request) {
        // TODO: 02.12.2022  if fields not valid - throws ConstraintViolationException.
        //  Without @Validated(Marker.) and @Validated on class is possible to use BindingResult

        //        if (bindingResult.hasErrors()) {
        //            throw new AttributeNotValidException("Ошибка валидации", bindingResult);
        //        }
        usersService.getUserByPhoneOrEmail(systemUser.getPhoneOrEmail()).ifPresent(u -> {
            throw new ElementAlreadyExistsException("Пользователь уже существует.");
        });
        User registeredUser = usersService.save(systemUser);
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, request.getLocale(), appUrl));
        return new ResponseEntity<>(new ApiMessage("Вы успешно зарегистрированы!"), HttpStatus.CREATED);
    }

    // TODO: 17.11.2022 fix cross origin 
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/auth")
    @Validated({Marker.OnCreate.class})
    public ResponseEntity<JwtResponse> createAuthToken(@Valid @RequestBody JwtRequest authRequest/*, BindingResult bindingResult*/) {
        //        if (bindingResult.hasErrors()) {
        //            throw new AttributeNotValidException("Ошибка валидации", bindingResult);
        //        }
        Authentication authentication = authenticate(authRequest.getPhoneOrEmail(), authRequest.getPassword());
        //} catch (BadCredentialsException badCredentialsException) {
        // TODO: 01.12.2022 fix double loadUserByUsername in this method
        UserDetails userDetails = usersService.loadUserByUsername(authRequest.getPhoneOrEmail());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<ApiMessage> confirmRegistration(WebRequest request, @RequestParam("token") String token) {
        Locale locale = request.getLocale();
        VerificationToken verificationToken = usersService.getVerificationToken(token);
        // TODO: 17.11.2022 fix duplicated lines 
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

    // TODO: 17.11.2022 GET with token is a bad idea 
    @GetMapping("/resendRegistrationToken")
    // TODO: 17.11.2022 choose unify response and use it 
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

    // TODO: 17.11.2022 GET with token is a bad idea
    @GetMapping("/changePassword")
    public ResponseEntity<ApiMessage> showChangePasswordPage(Locale locale, @RequestParam("token") String token) {
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
        // TODO: 17.11.2022 is it a good practice to send language to frontend?
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
    // TODO: 17.11.2022 configure annotation 
    //@PreAuthorize annotation, since it should only accessible to logged in users.
    public GenericResponse changeUserPassword(Locale locale, @RequestParam("password") String password, @RequestParam("oldpassword") String oldPassword) {
        User user = usersService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); //WTF?

        if (!usersService.isValidOldPassword(user, oldPassword)) {
            // TODO: 17.11.2022 configure exception handling
            //throw new InvalidOldPasswordException();
            return new GenericResponse("Invalid old password");
        }
        usersService.changeUserPassword(user, password);
        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }

    // TODO: 17.11.2022 use it instead of duplicated code above
    public String validatePasswordResetToken(String token) {
        //        final PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token);
        //
        //        return !isTokenFound(passToken) ? "invalidToken"
        //                : isTokenExpired(passToken) ? "expired"
        //                : null;
        return "";
    }

    // TODO: 17.11.2022 use or remove
    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }


    private Authentication authenticate(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
