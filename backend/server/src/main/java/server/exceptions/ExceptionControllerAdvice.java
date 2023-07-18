package server.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import server.entities.dtos.api.ApiError;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice implements WebMvcConfigurer {
    @ExceptionHandler
    public ResponseEntity<Error> handleJwtExpired(ExpiredJwtException exception) {
        log.error("JWT Token expired");
        return new ResponseEntity<>(new Error("JWT Token expired"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ElementAlreadyExistsException.class})
    public ResponseEntity<ApiError> handleElementAlreadyExistsException(final ElementAlreadyExistsException exception, final WebRequest request) {
        log.info("Trying to save an existing element.");
        ApiError apiError = new ApiError(exception.getLocalizedMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({AttributeNotValidException.class})
    public ResponseEntity<ApiError> handleAttributeNotValidException(final AttributeNotValidException exception, final WebRequest request) {
        log.info("Trying to save/modify an element with not valid fields.");
        final ApiError apiError = new ApiError(exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList()));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiError> handleBadCredentialsException(final BadCredentialsException exception, final WebRequest request) {
        log.info("Trying to authorize with invalid username or password. {}", exception.getLocalizedMessage());
        final ApiError apiError = new ApiError("Неверный логин или пароль!");
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleConstraintViolationExceptionException(final ConstraintViolationException exception, final WebRequest request) {
        log.info("Trying to save/modify an element with not valid fields.");
        /*error.getPropertyPath() + ": " + */
        final ApiError apiError = new ApiError(exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessageTemplate)
                .collect(Collectors.toList()));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ApiError> handleEntityNotFoundException(final EntityNotFoundException exception, final WebRequest request) {
        log.info("Trying to get non-existent entity.");
        final ApiError apiError = new ApiError(exception.getLocalizedMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({NonceExpiredException.class})
    public ResponseEntity<ApiError> handleNonceExpiredException(final NonceExpiredException exception, final WebRequest request) {
        log.info("Trying to use expired token.");
        final ApiError apiError = new ApiError(exception.getLocalizedMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ApiError> handleUsernameNotFoundExceptionException(final UsernameNotFoundException exception, final WebRequest request) {
        log.info("Trying to make action for authorized user only.");
        final ApiError apiError = new ApiError(exception.getLocalizedMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AuthenticationCredentialsNotFoundException.class})
    public ResponseEntity<ApiError> handleAuthenticationCredentialsNotFoundException(final AuthenticationCredentialsNotFoundException exception, final WebRequest request) {
        log.info("Trying to make action for authorized user only without Principal.");
        final ApiError apiError = new ApiError(exception.getLocalizedMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(final DataIntegrityViolationException exception, final WebRequest request) {
        log.info("Trying to save entity with duplicate entry.");
        final ApiError apiError = new ApiError(exception.getLocalizedMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

}
