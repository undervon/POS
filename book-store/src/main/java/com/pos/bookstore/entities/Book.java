package com.pos.bookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @NotNull(message = "Isbn cannot be null")
    @Size(min = 10, max = 13, message = "Isbn most be between 10 and 13 characters")
    @Column(unique = true)
    private String isbn;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 30, message = "Title most be between 1 and 30 characters")
    @Column(unique = true)
    private String title;

    @NotNull(message = "Publishing cannot be null")
    @Size(min = 1, max = 20, message = "Publishing most be between 1 and 20 characters")
    private String publishing;

    @NotNull(message = "PublicationYear cannot be null")
    @Min(value = 1, message = "PublicationYear should not be less than 1")
    private Integer publicationYear;

    @NotNull(message = "Genre cannot be null")
    @Size(min = 1, max = 10, message = "Genre most be between 1 and 10 characters")
    private String genre;

    @NotNull(message = "Stock cannot be null")
    @PositiveOrZero(message = "Stock should not be less than 0")
    private Integer stock;

    @NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price should not be less than 1")
    private Double price;

    @Transient
    @OneToMany(mappedBy = "book", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<BookAuthor> bookAuthorList = new ArrayList<>();
}
