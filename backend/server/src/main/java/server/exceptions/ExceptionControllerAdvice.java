package server.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import server.entities.dtos.ApiError;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
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


}
