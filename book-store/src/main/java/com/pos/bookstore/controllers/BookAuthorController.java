package com.pos.bookstore.controllers;

import com.pos.bookstore.models.AuthorIdIndexDTO;
import com.pos.bookstore.models.BookAuthorDTO;
import com.pos.bookstore.services.BookAuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/1.0/bookcollection")
public class BookAuthorController {

    private final BookAuthorService bookAuthorService;

    @CrossOrigin
    @GetMapping(path = "/books/{isbn}/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookAuthorDTO> getBooksAuthors(@PathVariable String isbn) {
        log.info(String.format("getBooksAuthors, isbn: %s", isbn));

        return ResponseEntity.status(HttpStatus.OK).body(bookAuthorService.getBooksAuthors(isbn));
    }

    @CrossOrigin
    @GetMapping(path = "/books/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookAuthorDTO>> getAllBooksAuthors() {
        log.info("getAllBooksAuthors");

        return ResponseEntity.status(HttpStatus.OK).body(bookAuthorService.getAllBooksAuthors());
    }

    @CrossOrigin
    @DeleteMapping(path = "/books/{isbn}/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteBooksAuthors(@PathVariable String isbn) {
        log.info(String.format("deleteBookAuthor, isbn: %s", isbn));

        bookAuthorService.deleteBooksAuthors(isbn);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @CrossOrigin
    @PostMapping(path = "/books/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookAuthorDTO> postBooksAuthors(@Valid @RequestBody BookAuthorDTO bookAuthorDTO) {
        log.info("postBooksAuthors");

        return ResponseEntity.status(HttpStatus.CREATED).body(bookAuthorService.postBooksAuthors(bookAuthorDTO));
    }

    @CrossOrigin
    @PutMapping(path = "/books/{isbn}/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putBooksAuthors(@PathVariable String isbn,
            @Valid @RequestBody AuthorIdIndexDTO authorIdIndexDTO) {
        log.info(String.format("putBooksAuthors, isbn: %s", isbn));

        bookAuthorService.putBooksAuthors(isbn, authorIdIndexDTO);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
