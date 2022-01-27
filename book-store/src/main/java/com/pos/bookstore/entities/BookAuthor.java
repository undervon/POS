package com.pos.bookstore.entities;

import com.pos.bookstore.entities.embedded.BookAuthorId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_author")
public class BookAuthor {

    @EmbeddedId
    private BookAuthorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookIsbn")
    @JoinColumn(name = "book_isbn")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "index_author")
    @NotNull(message = "Index author cannot be null")
    @Min(value = 1, message = "Index author should not be less than 1")
    private Integer indexAuthor;
}
