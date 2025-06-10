package ru.alexlemurski.service;

import ru.alexlemurski.entity.Author;
import ru.alexlemurski.layers.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    AuthorDto getAuthorById(long id);

    List<AuthorDto> getAllAuthors();

    Author createAuthor(String surName,
                        String name,
                        String middleName);

    void updateAuthor(Long id,
                      String surName,
                      String name,
                      String middleName);

    void deleteAuthor(Long id);

}