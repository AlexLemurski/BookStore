package ru.alexlemurski.layers.dto;

import lombok.Builder;

import java.util.List;

public record AuthorDto(
    Long id,
    String surName,
    String name,
    String middleName,
    List<BooksDto> bookList
) {
    @Builder
    public AuthorDto {
    }
}