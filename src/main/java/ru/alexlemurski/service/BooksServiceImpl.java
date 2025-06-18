package ru.alexlemurski.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.alexlemurski.entity.Books;
import ru.alexlemurski.layers.dto.BooksDto;
import ru.alexlemurski.repository.AuthorRepository;
import ru.alexlemurski.repository.BooksRepository;
import ru.alexlemurski.repository.GenreRepository;
import ru.alexlemurski.service_dto.AuthorDtoService;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;
    private final AuthorRepository authorRepository;
    private final AuthorDtoService authorDtoService;
    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public BooksDto getBooksById(Long id) {
        Books books = booksRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("bookstore.errors.book.not_found"));
        return BooksDto.builder()
            .id(books.getId())
            .bookName(books.getBookName())
            .author(authorDtoService.buildAuthorName(books.getAuthor()))
            .genre(books.getGenre().getGenreName())
            .build();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<BooksDto> getAllBooks() {
        List<Books> booksList = booksRepository.findAll();
        return booksList.stream().map(books -> BooksDto.builder()
                .id(books.getId())
                .bookName(books.getBookName())
                .author(authorDtoService.buildAuthorName(books.getAuthor()))
                .genre(books.getGenre().getGenreName())
                .build())
            .toList().stream()
            .sorted(Comparator.comparingLong(BooksDto::id))
            .toList();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Books createBook(String bookName, Long authorId, Long genreId) {
        var author = authorRepository.findById(authorId)
            .orElseThrow(() -> new NoSuchElementException("bookstore.errors.author.not_found"));
        var genre = genreRepository.findById(genreId)
            .orElseThrow(() -> new NoSuchElementException("bookstore.errors.genre.not_found"));
        Books books = new Books();
        books.setBookName(bookName);
        books.setAuthor(author);
        books.setGenre(genre);
        return booksRepository.save(books);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateBook(Long id, String bookName, Long authorId, Long genreId) {
        var author = authorRepository.findById(authorId)
            .orElseThrow(() -> new NoSuchElementException("bookstore.errors.author.not_found"));
        var genre = genreRepository.findById(genreId)
            .orElseThrow(() -> new NoSuchElementException("bookstore.errors.genre.not_found"));
        booksRepository.findById(id)
            .ifPresentOrElse(books -> {
                books.setBookName(bookName);
                books.setAuthor(author);
                books.setGenre(genre);
            }, () -> {
                throw new NoSuchElementException("bookstore.errors.book.not_found");
            });
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteBook(Long id) {
        Books books = booksRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("bookstore.errors.book.not_found"));
        booksRepository.deleteById(books.getId());
    }
}
