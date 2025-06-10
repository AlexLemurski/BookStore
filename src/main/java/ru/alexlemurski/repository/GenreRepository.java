package ru.alexlemurski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alexlemurski.entity.Genre;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("""
        select g from Genre as g
        where g.genreName=:name
        """)
    Optional<Genre> findByGenreName(@Param("name") String name);

}