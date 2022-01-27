package com.pos.bookstoreorder.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String isbn;
    private String title;
    private Double price;
    private Integer quantity;
}
