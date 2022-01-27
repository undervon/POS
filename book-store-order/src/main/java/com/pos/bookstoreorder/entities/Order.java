package com.pos.bookstoreorder.entities;

import com.pos.bookstoreorder.models.Book;
import com.pos.bookstoreorder.models.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "#{@orderRepository.getCollectionName()}")
public class Order {

    @Id
    private String id;

    private LocalDateTime date;
    private List<Book> items;
    private Status status;
}
