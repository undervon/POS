package com.pos.bookstore.entities.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class BookAuthorId implements Serializable {

    @Column(name = "book_isbn")
    private String bookIsbn;

    @Column(name = "author_id")
    private Long authorId;
}
