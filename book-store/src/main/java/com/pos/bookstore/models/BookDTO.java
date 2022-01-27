package com.pos.bookstore.models;

import com.pos.bookstore.models.validations.OnGeneral;
import com.pos.bookstore.models.validations.OnUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    @NotNull(message = "Isbn cannot be null", groups = {OnGeneral.class})
    @Null(message = "Isbn most be null on update", groups = {OnUpdate.class})
    @Size(min = 10, max = 13, message = "Isbn most be between 10 and 13 characters")
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
    @Min(value = 1, message = "Stock should not be less than 1")
    private Integer stock;

    @NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price should not be less than 1")
    private Double price;

    private List<AuthorDTO> authorDTOS;
}
