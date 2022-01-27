package com.pos.bookstore.services;

import com.pos.bookstore.entities.Author;
import com.pos.bookstore.exceptions.AuthorNotFoundException;
import com.pos.bookstore.models.AuthorDTO;
import com.pos.bookstore.models.BookDTO;
import com.pos.bookstore.repositories.AuthorRepository;
import com.pos.bookstore.utilities.Utilitati;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthorService {

    private final AuthorRepository authorRepository;

    protected AuthorDTO authorToAuthorDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .bookDTOS(author.getBookAuthorList().stream()
                        .map(authorBook -> BookDTO.builder()
                                .isbn(authorBook.getBook().getIsbn())
                                .title(authorBook.getBook().getTitle())
                                .publishing(authorBook.getBook().getPublishing())
                                .publicationYear(authorBook.getBook().getPublicationYear())
                                .genre(authorBook.getBook().getGenre())
                                .stock(authorBook.getBook().getStock())
                                .price(authorBook.getBook().getPrice())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public List<AuthorDTO> getAllAuthors() {
        log.info("getAllAuthors");

        List<Author> authors = authorRepository.findAll();

        return authors.stream()
                .map(this::authorToAuthorDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        log.info(String.format("getAuthorById, isbn: %s", id.toString()));

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id.toString()));

        return this.authorToAuthorDTO(author);
    }

    public void deleteAuthorById(Long id) {
        log.info(String.format("deleteAuthorById, id: %s", id.toString()));

        authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id.toString()));

        authorRepository.deleteById(id);
    }

    @Transactional
    public AuthorDTO createNewAuthor(AuthorDTO newAuthorDTO) {
        log.info(String.format("createNewAuthor, newAuthor: %s", newAuthorDTO.toString()));

        Author author = Author.builder()
                .firstName(newAuthorDTO.getFirstName())
                .lastName(newAuthorDTO.getLastName())
                .bookAuthorList(new ArrayList<>())
                .build();

        return this.authorToAuthorDTO(authorRepository.save(author));
    }

    @Transactional
    public void updateAuthor(AuthorDTO newAuthorDTO, Long id) {
        log.info(String.format("updateAuthor, id: %s", id.toString()));

        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (!optionalAuthor.isPresent()) {
            throw new AuthorNotFoundException(id.toString());
        }

        authorRepository.save(Author.builder()
                .id(id)
                .firstName(newAuthorDTO.getFirstName())
                .lastName(newAuthorDTO.getLastName())
                .bookAuthorList(optionalAuthor.get().getBookAuthorList())
                .build());
    }

    public List<AuthorDTO> getAllAuthorsByNameExact(Optional<String> name, Optional<String> match) {
        log.info("getAllAuthorsByNameExact");

        List<Author> authors;

        String authorName = Utilitati.AUTHOR_NAME;
        String authorMatch = Utilitati.AUTHOR_MATCH;

        if (name.isPresent()) {
            authorName = name.get();
        }

        if (match.isPresent()) {
            authorMatch = match.get();
        }

        if (authorMatch.equals("exact")) {
            authors = authorRepository.findAuthorsByLastName(authorName);
        } else {
            authors = this.findAuthorsByName(authorName);
        }

        return authors.stream()
                .map(this::authorToAuthorDTO)
                .collect(Collectors.toList());
    }

    public List<AuthorDTO> getAllAuthorsByName(Optional<String> name) {
        log.info("getAllAuthorsByNameExact");

        List<Author> newAuthors;

        String authorName = Utilitati.AUTHOR_NAME;

        if (name.isPresent()) {
            authorName = name.get();
        }

        newAuthors = this.findAuthorsByName(authorName);

        return newAuthors.stream()
                .map(this::authorToAuthorDTO)
                .collect(Collectors.toList());
    }

    // Find method (coll on getAllAuthorsByName and getAllAuthorsByNameExact)
    private List<Author> findAuthorsByName(String authorName) {
        List<Author> newAuthors = new ArrayList<>();
        List<Author> authors = authorRepository.findAll();

        for (Author author : authors) {
            if (author.getLastName().contains(authorName)) {
                newAuthors.add(author);
            }
        }

        return newAuthors;
    }
}
