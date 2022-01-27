package com.pos.bookstore.controllers;

import com.pos.bookstore.assemblers.BookAssembler;
import com.pos.bookstore.assemblers.BookVerboseAssambler;
import com.pos.bookstore.entities.Book;
import com.pos.bookstore.models.BookDTO;
import com.pos.bookstore.models.BookVerboseDTO;
import com.pos.bookstore.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/1.0/bookcollection")
public class BookController {

    private final BookService bookService;

    private final BookAssembler bookAssembler;
    private final BookVerboseAssambler bookVerboseAssambler;

    @CrossOrigin
    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<BookDTO>>> getAllBooks() {
        log.info("getAllBooks, get request");

        return ResponseEntity.status(HttpStatus.OK)
                .body(CollectionModel.of(bookService.getAllBooks().stream()
                                .map(bookAssembler::toModel)
                                .collect(Collectors.toList()),
                        linkTo(methodOn(BookController.class).getAllBooks()).withSelfRel()));
    }

    @CrossOrigin
    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE, params = {"page", "items_per_page"})
    public ResponseEntity<CollectionModel<EntityModel<BookDTO>>> getAllBooksByPage(
            @RequestParam(value = "page", required = false) Optional<String> page,
            @RequestParam(value = "items_per_page", required = false) Optional<String> itemsPerPage) {
        log.info("getAllBooksByPage, get request");

        return ResponseEntity.status(HttpStatus.OK)
                .body(CollectionModel.of(bookService.getAllBooksByPage(page, itemsPerPage).stream()
                                .map(bookAssembler::toModel)
                                .collect(Collectors.toList()),
                        linkTo(methodOn(BookController.class).getAllBooksByPage(page, itemsPerPage)).withSelfRel()));
    }

    @CrossOrigin
    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE, params = {"genre", "year"})
    public ResponseEntity<CollectionModel<EntityModel<BookDTO>>> getAllBooksByGenreAndYear(
            @RequestParam(value = "genre", required = false) Optional<String> genre,
            @RequestParam(value = "year", required = false) Optional<String> year) {
        log.info("getAllBooksByGenreAndYear, get request");

        return ResponseEntity.status(HttpStatus.OK)
                .body(CollectionModel.of(bookService.getAllBooksByGenreAndYear(genre, year).stream()
                                .map(bookAssembler::toModel)
                                .collect(Collectors.toList()),
                        linkTo(methodOn(BookController.class).getAllBooksByGenreAndYear(genre, year)).withSelfRel()));
    }

    @CrossOrigin
    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE, params = "genre")
    public ResponseEntity<CollectionModel<EntityModel<BookDTO>>> getAllBooksByGenre(
            @RequestParam(value = "genre", required = false) Optional<String> genre) {
        log.info("getAllBooksByGenre, get request");

        return ResponseEntity.status(HttpStatus.OK)
                .body(CollectionModel.of(bookService.getAllBooksByGenre(genre).stream()
                                .map(bookAssembler::toModel)
                                .collect(Collectors.toList()),
                        linkTo(methodOn(BookController.class).getAllBooksByGenre(genre)).withSelfRel()));
    }

    @CrossOrigin
    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE, params = "year")
    public ResponseEntity<CollectionModel<EntityModel<BookDTO>>> getAllBooksByYear(
            @RequestParam(value = "year", required = false) Optional<String> year) {
        log.info("getAllBooksByYear, get request");

        return ResponseEntity.status(HttpStatus.OK)
                .body(CollectionModel.of(bookService.getAllBooksByYear(year).stream()
                                .map(bookAssembler::toModel)
                                .collect(Collectors.toList()),
                        linkTo(methodOn(BookController.class).getAllBooksByYear(year)).withSelfRel()));
    }

    @CrossOrigin
    @GetMapping(path = "/books/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<BookDTO>> getBookByIsbn(@PathVariable String isbn) {
        log.info(String.format("getBookByIsbn, get request, isbn: %s", isbn));

        return ResponseEntity.status(HttpStatus.OK).body(bookAssembler.toModel(bookService.getBookByIsbn(isbn)));
    }

    @CrossOrigin
    @GetMapping(path = "/books/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE, params = "verbose")
    public ResponseEntity<EntityModel<BookVerboseDTO>> getBookByIsbnVerbose(@PathVariable String isbn,
            @RequestParam(value = "verbose", required = false) Optional<String> verbose) {
        log.info(String.format("getBookByIsbnVerbose, get request, isbn: %sm verbose: %s", isbn, verbose));

        return ResponseEntity.status(HttpStatus.OK).body(bookVerboseAssambler
                .toModel(bookService.getBookByIsbnVerbose(isbn, verbose)));
    }

    @Operation(summary = "Delete a book by isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The book has been successfully deleted", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Book.class))
            }),
            @ApiResponse(responseCode = "404", description = "The resource does not exist", content = @Content)
    })
    @CrossOrigin
    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> deleteBookByIsbn(@PathVariable String isbn) {
        log.info(String.format("deleteBookByIsbn, delete request, isbn: %s", isbn));

        bookService.deleteBookByIsbn(isbn);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

//    @PutMapping(path = "/books/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<EntityModel<BookDTO>> updateBook(@Valid @RequestBody BookDTO newBookDTO,
//            @PathVariable String isbn) {
//        log.info(String.format("updateBook, put request, isbn: %s", isbn));
//
//        EntityModel<BookDTO> bookDTOEntityModel = bookAssembler.toModel(bookService.updateBook(newBookDTO, isbn));
//
//        return ResponseEntity.created(bookDTOEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
//                .body(bookDTOEntityModel);
//    }
//
//    @PostMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<EntityModel<BookDTO>> createBook(@Valid @RequestBody BookDTO newBookDTO) {
//        log.info("createBook, post request");
//
//        EntityModel<BookDTO> bookDTOEntityModel = bookAssembler.toModel(bookService.createBook(newBookDTO));
//
//        return ResponseEntity.created(bookDTOEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
//                .body(bookDTOEntityModel);
//    }

    @CrossOrigin
    @PutMapping(path = "/books/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<BookDTO>> createOrUpdateBook(@Valid @RequestBody BookDTO newBookDTO,
            @PathVariable String isbn) {
        log.info(String.format("updateBook, put request, isbn: %s", isbn));

        EntityModel<BookDTO> bookDTOEntityModel = bookAssembler.toModel(
                bookService.createOrUpdateBook(newBookDTO, isbn));

        return ResponseEntity.created(bookDTOEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(bookDTOEntityModel);
    }
}
