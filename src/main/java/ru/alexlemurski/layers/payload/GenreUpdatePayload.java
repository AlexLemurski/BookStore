package ru.alexlemurski.layers.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static ru.alexlemurski.auxiliary.GenreDtoService.*;

public record GenreUpdatePayload(

    @NotBlank(message = "{bookstore.errors.not_blank}")
    @Pattern(regexp = genreRegexExpressionRu,
        message = "{bookstore.errors.only_on_ru}")
    @Size(min = genreMinValue, max = genreMaxValue,
        message = "{bookstore.errors.size_min_to_max}")
    String genreName

) {
}