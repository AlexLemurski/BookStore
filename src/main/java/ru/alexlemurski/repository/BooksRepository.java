package ru.alexlemurski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alexlemurski.entity.Books;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {

    @Query("""
        select b from Books b
        where b.author.id=:id
        order by b.id
        """)
    List<Books> findBooksByAuthorId(@Param("id") Long id);

}