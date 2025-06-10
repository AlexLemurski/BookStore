package ru.alexlemurski.layers.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static ru.alexlemurski.auxiliary.AuthorDtoService.*;

public record AuthorUpdatePayload(

    @NotBlank(message = "{bookstore.errors.not_blank}")
    @Pattern(regexp = authorRegexExpressionRu, message = "{bookstore.errors.only_on_ru}")
    @Size(min = authorMinValue, max = authorMaxValue, message = "{bookstore.errors.size_min}")
    String surName,

    @NotBlank(message = "{bookstore.errors.not_blank}")
    @Pattern(regexp = authorRegexExpressionRu, message = "{bookstore.errors.only_on_ru}")
    @Size(min = authorMinValue, max = authorMaxValue, message = "{bookstore.errors.size_min}")
    String name,

    @NotBlank(message = "{bookstore.errors.not_blank}")
    @Pattern(regexp = authorRegexExpressionRu, message = "{bookstore.errors.only_on_ru}")
    @Size(min = authorMinValue, max = authorMaxValue, message = "{bookstore.errors.size_min}")
    String middleName

) {
}