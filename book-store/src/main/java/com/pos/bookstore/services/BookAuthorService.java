package com.pos.bookstore.services;

import com.pos.bookstore.entities.Author;
import com.pos.bookstore.entities.Book;
import com.pos.bookstore.entities.BookAuthor;
import com.pos.bookstore.entities.embedded.BookAuthorId;
import com.pos.bookstore.exceptions.BookNotFoundException;
import com.pos.bookstore.models.AuthorIdIndexDTO;
import com.pos.bookstore.models.BookAuthorDTO;
import com.pos.bookstore.repositories.BookAuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookAuthorService {

    private final BookAuthorRepository bookAuthorRepository;

    private final BookService bookService;

    protected BookAuthorDTO bookAuthorToBookAuthorDTO(BookAuthor bookAuthor) {
        return BookAuthorDTO.builder()
                .isbn(bookAuthor.getBook().getIsbn())
                .idAuthor(bookAuthor.getAuthor().getId())
                .indexAuthor(bookAuthor.getIndexAuthor())
                .build();
    }

    public BookAuthorDTO getBooksAuthors(String isbn) {
        log.info("getBooksAuthors");

        return BookAuthorDTO.builder()
                .isbn(isbn)
                .idAuthor(bookService.getBookByIsbn(isbn).getAuthorDTOS().get(0).getId())
                .indexAuthor(
                        bookAuthorRepository.findFirstByBookIsbn(isbn)
                                .orElseThrow(() -> new BookNotFoundException(isbn)).getIndexAuthor())
                .build();
    }

    public List<BookAuthorDTO> getAllBooksAuthors() {
        log.info("getAllBooksAuthors");

        return bookAuthorRepository.findAll().stream()
                .map(this::bookAuthorToBookAuthorDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteBooksAuthors(String isbn) {
        log.info("deleteBooksAuthors");

        bookAuthorRepository.deleteBookAuthorsByBook_Isbn(isbn);
    }

    @Transactional
    public BookAuthorDTO postBooksAuthors(BookAuthorDTO bookAuthorDTO) {
        log.info("postBooksAuthors");

        BookAuthor bookAuthor = BookAuthor.builder()
                .id(new BookAuthorId())
                .book(Book.builder()
                        .isbn(bookAuthorDTO.getIsbn())
                        .build())
                .author(Author.builder()
                        .id(bookAuthorDTO.getIdAuthor())
                        .build())
                .indexAuthor(bookAuthorDTO.getIndexAuthor())
                .build();

        return this.bookAuthorToBookAuthorDTO(bookAuthorRepository.save(bookAuthor));
    }

    @Transactional
    public void putBooksAuthors(String isbn, AuthorIdIndexDTO authorIdIndexDTO) {
        log.info("putBooksAuthors");

        BookAuthor initialBookAuthor =
                bookAuthorRepository.findFirstByBookIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));

        BookAuthor bookAuthor = BookAuthor.builder()
                .id(initialBookAuthor.getId())
                .book(initialBookAuthor.getBook())
                .author(Author.builder()
                        .id(authorIdIndexDTO.getIdAuthor())
                        .build())
                .indexAuthor(authorIdIndexDTO.getIndexAuthor())
                .build();
        bookAuthorRepository.save(bookAuthor);
    }
}
