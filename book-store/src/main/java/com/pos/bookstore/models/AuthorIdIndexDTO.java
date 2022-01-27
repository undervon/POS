package com.pos.bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorIdIndexDTO {

    @NotNull(message = "Id cannot be null")
    @Min(value = 1, message = "Id author should not be less than 1")
    private Long idAuthor;

    @NotNull(message = "Index author cannot be null")
    @Min(value = 1, message = "Index author should not be less than 1")
    private Integer indexAuthor;
}
