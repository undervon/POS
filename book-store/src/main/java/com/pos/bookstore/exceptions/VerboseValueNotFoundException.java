package com.pos.bookstore.exceptions;

public class VerboseValueNotFoundException extends RuntimeException {

    public VerboseValueNotFoundException(String message) {
        super(message);
    }
}
