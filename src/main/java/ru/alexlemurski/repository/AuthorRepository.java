package ru.alexlemurski.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexlemurski.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}