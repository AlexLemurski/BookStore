package ru.alexlemurski.service;

import ru.alexlemurski.entity.Genre;
import ru.alexlemurski.layers.dto.GenreDto;

import java.util.List;

public interface GenreService {

    GenreDto getGenreById(Long id);

    List<GenreDto> getAllGenres();

    Genre createGenre(String genreName);

    void updateGenre(Long id,
                     String genreName);

    void deleteGenre(Long id);

}
