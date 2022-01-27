package com.pos.bookstore.repositories;

import com.pos.bookstore.entities.BookAuthor;
import com.pos.bookstore.entities.embedded.BookAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorId> {

    Optional<BookAuthor> findFirstByBookIsbn(String isbn);

    void deleteBookAuthorsByBook_Isbn(String isbn);
}
