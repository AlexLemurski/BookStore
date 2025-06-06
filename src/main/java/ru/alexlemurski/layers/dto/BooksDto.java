package ru.alexlemurski.layers.dto;

import lombok.Builder;

public record BooksDto(
    Long id,
    String bookName,
    String author,
    String genre
) {
    @Builder
    public BooksDto {
    }
}