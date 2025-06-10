package ru.alexlemurski.layers.dto;

import lombok.Builder;

public record GenreDto(
    Long id,
    String genreName
) {
    @Builder
    public GenreDto {
    }
}