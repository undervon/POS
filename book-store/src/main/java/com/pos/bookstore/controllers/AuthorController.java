package com.pos.bookstore.controllers;

import com.pos.bookstore.assemblers.AuthorAssembler;
import com.pos.bookstore.entities.Author;
import com.pos.bookstore.models.AuthorDTO;
import com.pos.bookstore.services.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/1.0/bookcollection")
public class AuthorController {

    private final AuthorService authorService;

    private final AuthorAssembler authorAssembler;

    @CrossOrigin
    @GetMapping(path = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<AuthorDTO>>> getAllAuthors() {
        log.info("getAllAuthors, get request");

        return ResponseEntity.status(HttpStatus.OK).body(CollectionModel.of(authorService.getAllAuthors().stream()
                        .map(authorAssembler::toModel)
                        .collect(Collectors.toList()),
                linkTo(methodOn(AuthorController.class).getAllAuthors()).withSelfRel()));
    }

    @CrossOrigin
    @GetMapping(path = "/authors", produces = MediaType.APPLICATION_JSON_VALUE, params = {"name", "match"})
    public ResponseEntity<CollectionModel<EntityModel<AuthorDTO>>> getAllAuthorsByNameExact(
            @RequestParam(value = "name", required = false) Optional<String> name,
            @RequestParam(value = "match", required = false) Optional<String> match) {
        log.info("getAllAuthorsByNameExact, get request");

        return ResponseEntity.status(HttpStatus.OK)
                .body(CollectionModel.of(authorService.getAllAuthorsByNameExact(name, match).stream()
                                .map(authorAssembler::toModel)
                                .collect(Collectors.toList()),
                        linkTo(methodOn(AuthorController.class).getAllAuthorsByNameExact(name, match)).withSelfRel()));
    }

    @CrossOrigin
    @GetMapping(path = "/authors", produces = MediaType.APPLICATION_JSON_VALUE, params = "name")
    public ResponseEntity<CollectionModel<EntityModel<AuthorDTO>>> getAllAuthorsByName(
            @RequestParam(value = "name", required = false) Optional<String> name) {
        log.info("getAllAuthorsByName, get request");

        return ResponseEntity.status(HttpStatus.OK)
                .body(CollectionModel.of(authorService.getAllAuthorsByName(name).stream()
                                .map(authorAssembler::toModel)
                                .collect(Collectors.toList()),
                        linkTo(methodOn(AuthorController.class).getAllAuthorsByName(name)).withSelfRel()));
    }

    @CrossOrigin
    @GetMapping(path = "/authors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<AuthorDTO>> getAuthorById(@PathVariable Long id) {
        log.info(String.format("getAuthorById, get request, id: %s", id.toString()));

        return ResponseEntity.status(HttpStatus.OK).body(authorAssembler.toModel(authorService.getAuthorById(id)));
    }

    @CrossOrigin
    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<Author> deleteAuthorById(@PathVariable Long id) {
        log.info(String.format("deleteAuthorById, delete request, id: %s", id.toString()));

        authorService.deleteAuthorById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @CrossOrigin
    @PostMapping(path = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<AuthorDTO>> createNewAuthor(@Valid @RequestBody AuthorDTO newAuthorDTO) {
        log.info(String.format("createNewAuthor, post request, author: %s", newAuthorDTO.toString()));

        EntityModel<AuthorDTO> authorEntityModel = authorAssembler.toModel(authorService.createNewAuthor(newAuthorDTO));

        return ResponseEntity.created(authorEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(authorEntityModel);
    }

    @CrossOrigin
    @PutMapping(path = "/authors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<AuthorDTO>> updateAuthor(@Valid @RequestBody AuthorDTO newAuthorDTO,
            @PathVariable Long id) {
        log.info(String.format("updateAuthor, put request, id: %s", id.toString()));

        authorService.updateAuthor(newAuthorDTO, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
