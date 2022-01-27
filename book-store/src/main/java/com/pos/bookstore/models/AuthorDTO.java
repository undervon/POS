package com.pos.bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    @Null(message = "Id most be null")
    @Min(value = 1, message = "Id should not be less than 1")
    private Long id;

    @NotNull(message = "First name cannot be null")
    @Size(min = 1, max = 20, message = "First name most be between 1 and 20 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 1, max = 20, message = "Last name most be between 1 and 20 characters")
    private String lastName;

    private List<BookDTO> bookDTOS;
}
