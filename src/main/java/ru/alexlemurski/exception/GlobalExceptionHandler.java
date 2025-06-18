package ru.alexlemurski.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
@RequiredArgsConstructor
final class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ProblemDetail problemDetail = ProblemDetail
            .forStatusAndDetail(HttpStatus.BAD_REQUEST, "bookstore.errors.incorrect_data_input");
        problemDetail.setProperty("errors", errors);
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleException(NoSuchElementException exception,
                                                         Locale locale) {
        ProblemDetail problemDetail = ProblemDetail
            .forStatusAndDetail(HttpStatus.NOT_FOUND, messageSource.getMessage(exception.getMessage(),
                new Object[0], exception.getMessage(), locale));
        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenreUniqueCreateException.class)
    public ResponseEntity<ProblemDetail> handleException(GenreUniqueCreateException exception,
                                                         Locale locale) {
        ProblemDetail problemDetail = ProblemDetail
            .forStatusAndDetail(HttpStatus.BAD_REQUEST, messageSource.getMessage(exception.getMessage(),
                new Object[0], exception.getMessage(), locale));
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenreUniqueUpdateException.class)
    public ResponseEntity<ProblemDetail> handleException(GenreUniqueUpdateException exception,
                                                         Locale locale) {
        ProblemDetail problemDetail = ProblemDetail
            .forStatusAndDetail(HttpStatus.BAD_REQUEST, messageSource.getMessage(exception.getMessage(),
                new Object[0], exception.getMessage(), locale));
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }
}