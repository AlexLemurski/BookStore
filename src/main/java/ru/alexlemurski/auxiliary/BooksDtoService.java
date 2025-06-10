package ru.alexlemurski.auxiliary;

import org.springframework.stereotype.Service;
import ru.alexlemurski.entity.Author;
import ru.alexlemurski.entity.Books;
import ru.alexlemurski.layers.dto.BooksDto;
import ru.alexlemurski.repository.BooksRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BooksDtoService {

    public static final int booksMinValue = 2;
    public static final int booksMaxValue = 50;
    public static final String booksRegexExpressionRu = "^\\p{IsCyrillic}+$";

    public final List<BooksDto> buildBookGenreName(Author author, BooksRepository booksRepository) {
        List<Books> booksList = booksRepository.findBooksByAuthorId(author.getId());
        return new ArrayList<>(booksList.stream()
            .map(book -> BooksDto.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .genre(book.getGenre().getGenreName())
                .build())
            .toList());
    }
}