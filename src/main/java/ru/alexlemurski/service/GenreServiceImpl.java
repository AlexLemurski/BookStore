package ru.alexlemurski.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.alexlemurski.entity.Genre;
import ru.alexlemurski.exception.GenreUniqueCreateException;
import ru.alexlemurski.exception.GenreUniqueUpdateException;
import ru.alexlemurski.layers.dto.GenreDto;
import ru.alexlemurski.repository.GenreRepository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("bookstore.errors.genre.not_found"));
        return GenreDto.builder()
            .id(genre.getId())
            .genreName(genre.getGenreName())
            .build();
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<GenreDto> getAllGenres() {
        List<Genre> genreList = genreRepository.findAll();
        return genreList.stream().map(genre -> GenreDto.builder()
                .id(genre.getId())
                .genreName(genre.getGenreName())
                .build())
            .toList().stream()
            .sorted(Comparator.comparingLong(GenreDto::id))
            .toList();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Genre createGenre(String genreName) {
        Genre genre = new Genre();
        genre.setGenreName(genreName);
        try {
            return genreRepository.save(genre);
        } catch (DataIntegrityViolationException e) {
            throw new GenreUniqueCreateException("bookstore.genre.genre_name_unique_error");
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateGenre(Long id, String genreName) {
        var genre = genreRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("bookstore.errors.genre.not_found"));
        var genreForUpdate = genreRepository.findByGenreName(genreName)
            .orElseThrow(() -> new NoSuchElementException("bookstore.errors.genre.not_found"));
        if (genreForUpdate != null && !Objects.equals(genreForUpdate.getId(), id)) {
            throw new GenreUniqueUpdateException(id, genreName, "bookstore.genre.genre_name_unique_error");
        } else {
            genre.setGenreName(genreName);
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteGenre(Long id) {
        Genre genre = genreRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("bookstore.errors.genre.not_found"));
        genreRepository.deleteById(genre.getId());
    }
}
