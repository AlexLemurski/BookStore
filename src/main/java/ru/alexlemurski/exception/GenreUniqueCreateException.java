package ru.alexlemurski.exception;

import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

@Getter
public class GenreUniqueCreateException extends DataIntegrityViolationException {

    public GenreUniqueCreateException(String msg) {
        super(msg);
    }
}