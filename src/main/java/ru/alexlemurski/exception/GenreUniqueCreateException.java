package ru.alexlemurski.exception;

import lombok.Getter;
import org.springframework.dao.DataIntegrityViolationException;

@Getter
public final class GenreUniqueCreateException extends DataIntegrityViolationException {

    public GenreUniqueCreateException(String msg) {
        super(msg);
    }
}