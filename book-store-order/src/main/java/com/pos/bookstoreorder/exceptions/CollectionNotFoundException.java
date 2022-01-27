package com.pos.bookstoreorder.exceptions;

public class CollectionNotFoundException extends RuntimeException {

    public CollectionNotFoundException(String message) {
        super(message);
    }
}
