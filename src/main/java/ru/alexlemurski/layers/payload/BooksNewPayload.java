package ru.alexlemurski.layers.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static ru.alexlemurski.service_dto.BooksDtoService.*;

public record BooksNewPayload(

    @NotBlank(message = "{bookstore.errors.not_blank}")
    @Pattern(regexp = booksRegexExpressionRu, message = "{bookstore.errors.only_on_ru}")
    @Size(min = booksMinValue, max = booksMaxValue, message = "{bookstore.errors.size_min_to_max}")
    String bookName,

    Long authorId,

    Long genreId

) {
}