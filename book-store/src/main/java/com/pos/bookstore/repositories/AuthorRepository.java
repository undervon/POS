package com.pos.bookstore.repositories;

import com.pos.bookstore.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAuthorsByLastName(String authorLastName);
}
