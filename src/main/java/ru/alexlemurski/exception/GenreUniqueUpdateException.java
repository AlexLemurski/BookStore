package ru.alexlemurski.exception;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;

@Getter
public final class GenreUniqueUpdateException extends DataIntegrityViolationException {

    private final Long genreId;
    private final String genreName;

    public GenreUniqueUpdateException(@NotNull Long genreId, String genreName, String msg) {
        super(msg);
        this.genreId = genreId;
        this.genreName = genreName;
    }

}
