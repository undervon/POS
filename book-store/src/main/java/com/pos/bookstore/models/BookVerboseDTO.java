package com.pos.bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookVerboseDTO {

    @NotNull(message = "Isbn cannot be null")
    @Size(min = 10, max = 13, message = "Isbn most be between 10 and 13 characters")
    private String isbn;

    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 30, message = "Title most be between 1 and 30 characters")
    private String title;

    @NotNull(message = "Genre cannot be null")
    @Size(min = 1, max = 10, message = "Genre most be between 1 and 10 characters")
    private String genre;
}
