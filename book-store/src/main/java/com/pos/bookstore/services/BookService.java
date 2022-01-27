package com.pos.bookstore.services;

import com.pos.bookstore.entities.Book;
import com.pos.bookstore.exceptions.BookNotFoundException;
import com.pos.bookstore.exceptions.VerboseValueNotFoundException;
import com.pos.bookstore.models.AuthorDTO;
import com.pos.bookstore.models.BookDTO;
import com.pos.bookstore.models.BookVerboseDTO;
import com.pos.bookstore.repositories.BookRepository;
import com.pos.bookstore.utilities.Utilitati;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
@Log4j2
public class BookService {

    private final BookRepository bookRepository;

    protected BookDTO bookToBookDTO(Book book) {
        return BookDTO.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .publishing(book.getPublishing())
                .publicationYear(book.getPublicationYear())
                .genre(book.getGenre())
                .stock(book.getStock())
                .price(book.getPrice())
                .authorDTOS(book.getBookAuthorList().stream()
                        .map(authorBook -> AuthorDTO.builder()
                                .id(authorBook.getAuthor().getId())
                                .firstName(authorBook.getAuthor().getFirstName())
                                .lastName(authorBook.getAuthor().getLastName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    protected BookVerboseDTO bookToBookVerboseDTO(Book book) {
        return BookVerboseDTO.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .genre(book.getGenre())
                .build();
    }

    public List<BookDTO> getAllBooks() {
        log.info("getAllBooks");

        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(this::bookToBookDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookByIsbn(String isbn) {
        log.info(String.format("getBookByIsbn, isbn: %s", isbn));

        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        return this.bookToBookDTO(book);
    }

    public void deleteBookByIsbn(String isbn) {
        log.info(String.format("deleteBookByIsbn, isbn: %s", isbn));

        bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        bookRepository.deleteById(isbn);
    }

//    @Validated(OnUpdate.class)
//    @Transactional
//    public BookDTO updateBook(@Valid BookDTO newBookDTO, String isbn) {
//        log.info(String.format("updateBook, isbn: %s", isbn));
//
//        Optional<Book> optionalBook = bookRepository.findById(isbn);
//
//        optionalBook.orElseThrow(() -> new BookNotFoundException(isbn));
//
//        return this.bookToBookDTO(bookRepository.save(Book.builder()
//                .isbn(isbn)
//                .title(newBookDTO.getTitle())
//                .publishing(newBookDTO.getPublishing())
//                .publicationYear(newBookDTO.getPublicationYear())
//                .genre(newBookDTO.getGenre())
//                .bookAuthorList(optionalBook.get().getBookAuthorList())
//                .build()));
//    }
//
//    @Transactional
//    public BookDTO createBook(BookDTO newBookDTO) {
//        log.info(String.format("createBook, book: %s", newBookDTO.toString()));
//
//        return this.bookToBookDTO(bookRepository.save(Book.builder()
//                .isbn(newBookDTO.getIsbn())
//                .title(newBookDTO.getTitle())
//                .publishing(newBookDTO.getPublishing())
//                .publicationYear(newBookDTO.getPublicationYear())
//                .genre(newBookDTO.getGenre())
//                .bookAuthorList(new ArrayList<>())
//                .build()));
//    }

    @Transactional
    public BookDTO createOrUpdateBook(BookDTO newBookDTO, String isbn) {
        log.info(String.format("createOrUpdateBook, isbn: %s", isbn));

        Optional<Book> optionalBook = bookRepository.findById(isbn);

        try {
            log.info("createOrUpdateBook -> update");

            return this.bookToBookDTO(bookRepository.save(Book.builder()
                    .isbn(isbn)
                    .title(newBookDTO.getTitle())
                    .publishing(newBookDTO.getPublishing())
                    .publicationYear(newBookDTO.getPublicationYear())
                    .genre(newBookDTO.getGenre())
                    .stock(newBookDTO.getStock())
                    .price(newBookDTO.getPrice())
                    .bookAuthorList(optionalBook.get().getBookAuthorList())
                    .build()));
        } catch (NoSuchElementException noSuchElementException) {
            log.info("createOrUpdateBook -> create");

            return this.bookToBookDTO(bookRepository.save(Book.builder()
                    .isbn(newBookDTO.getIsbn())
                    .title(newBookDTO.getTitle())
                    .publishing(newBookDTO.getPublishing())
                    .publicationYear(newBookDTO.getPublicationYear())
                    .genre(newBookDTO.getGenre())
                    .stock(newBookDTO.getStock())
                    .price(newBookDTO.getPrice())
                    .bookAuthorList(new ArrayList<>())
                    .build()));
        }
    }

    public List<BookDTO> getAllBooksByPage(Optional<String> page, Optional<String> itemsPerPage) {
        log.info("getAllBooksByPage");

        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOS = new ArrayList<>();

        Integer numberOfItemsPerPage = Utilitati.BOOK_NUMBER_OF_ITEMS_PER_PAGE;
        Integer numberOfPage = Utilitati.BOOK_NUMBER_OF_PAGE;
        Integer nextPage;

        if (itemsPerPage.isPresent()) {
            numberOfItemsPerPage = Integer.parseInt(itemsPerPage.get());
        }

        if (page.isPresent()) {
            numberOfPage = Integer.parseInt(page.get());
        }

        nextPage = numberOfPage * numberOfItemsPerPage;

        while (numberOfItemsPerPage != 0) {
            bookDTOS.add(this.bookToBookDTO(books.get(nextPage++)));
            numberOfItemsPerPage--;
        }

        return bookDTOS;
    }

    public List<BookDTO> getAllBooksByGenreAndYear(Optional<String> genre, Optional<String> year) {
        log.info("getAllBooksByGenreAndYear");

        List<Book> books;

        String bookGenre = Utilitati.BOOK_GENRE;
        Integer bookYear = Utilitati.BOOK_YEAR;

        if (genre.isPresent()) {
            bookGenre = genre.get();
        }

        if (year.isPresent()) {
            bookYear = Integer.parseInt(year.get());
        }

        books = bookRepository.findBooksByPublicationYearAndGenre(bookYear, bookGenre);

        return books.stream()
                .map(this::bookToBookDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> getAllBooksByGenre(Optional<String> genre) {
        log.info("getAllBooksByGenre");

        List<Book> books;

        String bookGenre = Utilitati.BOOK_GENRE;

        if (genre.isPresent()) {
            bookGenre = genre.get();
        }

        books = bookRepository.findBooksByGenre(bookGenre);

        return books.stream()
                .map(this::bookToBookDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> getAllBooksByYear(Optional<String> year) {
        log.info("getAllBooksByYear");

        List<Book> books;

        Integer bookYear = Utilitati.BOOK_YEAR;

        if (year.isPresent()) {
            bookYear = Integer.parseInt(year.get());
        }

        books = bookRepository.findBooksByPublicationYear(bookYear);

        return books.stream()
                .map(this::bookToBookDTO)
                .collect(Collectors.toList());
    }

    public BookVerboseDTO getBookByIsbnVerbose(String isbn, Optional<String> verbose) {
        log.info(String.format("getBookByIsbnVerbose, isbn: %s, verbose: %s", isbn, verbose));

        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        BookVerboseDTO bookVerboseDTO = null;

        if (verbose.isPresent() && verbose.get().equals(Utilitati.BOOK_VERBOSE)) {
            bookVerboseDTO = this.bookToBookVerboseDTO(book);
        } else {
            if (verbose.isPresent() && !verbose.get().equals(Utilitati.BOOK_VERBOSE)) {
                throw new VerboseValueNotFoundException(verbose.get());
            }
        }

        return bookVerboseDTO;
    }
}
