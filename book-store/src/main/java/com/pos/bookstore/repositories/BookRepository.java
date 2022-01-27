package com.pos.bookstore.repositories;

import com.pos.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findBooksByPublicationYearAndGenre(Integer bookYear, String bookGenre);

    List<Book> findBooksByGenre(String bookGenre);

    List<Book> findBooksByPublicationYear(Integer bookYear);
}
