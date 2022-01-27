package com.pos.bookstore.exceptions;

public class AuthorAlreadyExistsException extends RuntimeException {

    public AuthorAlreadyExistsException(String message) {
        super(message);
    }
}
