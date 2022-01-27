package com.pos.bookstoreorder.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CollectionNotFoundException.class)
    public ResponseEntity<String> bookNotFoundHandler(CollectionNotFoundException collectionNotFoundException) {
        log.error("thrown CollectionNotFoundException");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Could not found collection for client id: %s",
                        collectionNotFoundException.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> insufficientStockHandler(InsufficientStockException insufficientStockException) {
        log.error("thrown InsufficientStockException");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Book with isbn: %s does not have the required stock",
                        insufficientStockException.getMessage()));
    }
}
