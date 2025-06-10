package ru.alexlemurski.service;

import ru.alexlemurski.entity.Books;
import ru.alexlemurski.layers.dto.BooksDto;

import java.util.List;

public interface BooksService {

    BooksDto getBooksById(Long id);

    List<BooksDto> getAllBooks();

    Books createBook(String bookName,
                     Long authorId,
                     Long genreId);

    void updateBook(Long id,
                    String bookName,
                    Long authorId,
                    Long genreId);

    void deleteBook(Long id);

}