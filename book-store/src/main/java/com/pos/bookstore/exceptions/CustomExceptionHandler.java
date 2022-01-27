package com.pos.bookstore.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@Log4j2
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> bookNotFoundHandler(BookNotFoundException bookNotFoundException) {
        log.error("thrown BookNotFoundException");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Book with ISBN: %s not found",
                bookNotFoundException.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<String> authorNotFoundHandler(AuthorNotFoundException authorNotFoundException) {
        log.error("thrown AuthorNotFoundException");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Author with id: %s not found",
                authorNotFoundException.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<String> bookAlreadyExistsHandler(BookAlreadyExistsException bookAlreadyExistsException) {
        log.error("thrown BookAlreadyExistsException");

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(String.format("Book with isbn: %s already exists",
                bookAlreadyExistsException.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(AuthorAlreadyExistsException.class)
    public ResponseEntity<String> authorAlreadyExistsHandler(
            AuthorAlreadyExistsException authorAlreadyExistsException) {
        log.error("thrown AuthorAlreadyExistsException");

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(String.format("Author with id: %s already "
                + "exists", authorAlreadyExistsException.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidHandler(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("thrown MethodArgumentNotValidException");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(methodArgumentNotValidException.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationHandler(
            ConstraintViolationException constraintViolationException) {
        log.error("thrown ConstraintViolationException");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(constraintViolationException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> SQLIntegrityConstraintViolationHandler(
            SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
        log.error("thrown SQLIntegrityConstraintViolationException");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(sqlIntegrityConstraintViolationException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(VerboseValueNotFoundException.class)
    public ResponseEntity<String> verboseValueNotFoundHandler(
            VerboseValueNotFoundException verboseValueNotFoundException) {
        log.error("thrown VerboseValueNotFoundException");

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(String.format("Verbose value: %s not found",
                verboseValueNotFoundException.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<String> indexOutOfBoundsHandler(IndexOutOfBoundsException indexOutOfBoundsException) {
        log.error("thrown IndexOutOfBoundsException");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(indexOutOfBoundsException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> numberFormatHandler(NumberFormatException numberFormatException) {
        log.error("thrown NumberFormatException");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("This format %s is not supported",
                numberFormatException.getMessage().substring(18)));
    }
}
