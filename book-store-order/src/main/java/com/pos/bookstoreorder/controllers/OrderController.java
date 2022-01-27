package com.pos.bookstoreorder.controllers;

import com.pos.bookstoreorder.entities.Order;
import com.pos.bookstoreorder.models.Book;
import com.pos.bookstoreorder.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/1.0/bookcollection")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders/{id}")
    public ResponseEntity<List<Order>> getAllOrdersByClientId(@PathVariable("id") Long id) {
        log.info("[{}] -> GET, getAllOrdersByClientId, client id: {}", this.getClass().getSimpleName(), id);

        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrdersByClientId(id));
    }

    @PostMapping("/order/{id}")
    public ResponseEntity<Order> createOrder(@PathVariable("id") Long id, @RequestBody List<Book> books) {
        log.info("[{}] -> POST, createOrder, client id: {}, book list: {}",
                this.getClass().getSimpleName(), id, books);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(id, books));
    }
}
