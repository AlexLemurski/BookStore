package ru.alexlemurski.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.alexlemurski.service_dto.BooksDtoService;
import ru.alexlemurski.entity.Author;
import ru.alexlemurski.layers.dto.AuthorDto;
import ru.alexlemurski.repository.AuthorRepository;
import ru.alexlemurski.repository.BooksRepository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BooksRepository booksRepository;
    private final BooksDtoService booksDtoService;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public AuthorDto getAuthorById(long id) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("bookstore.errors.author.not_found"));
        return AuthorDto.builder()
            .id(author.getId())
            .name(author.getName())
            .middleName(author.getMiddleName())
            .surName(author.getSurName())
            .bookList(booksDtoService.buildBookGenreName(author, booksRepository))
            .build();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(author -> AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .middleName(author.getMiddleName())
                .surName(author.getSurName())
                .bookList(booksDtoService.buildBookGenreName(author, booksRepository))
                .build()).toList().stream()
            .sorted(Comparator.comparingLong(AuthorDto::id))
            .toList();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Author createAuthor(String name, String surName, String middleName) {
        Author author = new Author();
        author.setName(name);
        author.setSurName(surName);
        author.setMiddleName(middleName);
        return authorRepository.save(author);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateAuthor(Long id, String name, String surName, String middleName) {
        authorRepository.findById(id)
            .ifPresentOrElse(author -> {
                author.setName(name);
                author.setSurName(surName);
                author.setMiddleName(middleName);
            }, () -> {
                throw new NoSuchElementException("bookstore.errors.author.not_found");
            });
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("bookstore.errors.author.not_found"));
        authorRepository.deleteById(author.getId());
    }
}