package com.pos.bookstore.assemblers;

import com.pos.bookstore.controllers.BookController;
import com.pos.bookstore.models.BookDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookAssembler implements RepresentationModelAssembler<BookDTO, EntityModel<BookDTO>> {

    @Override
    public @NotNull EntityModel<BookDTO> toModel(@NotNull BookDTO book) {
        return EntityModel.of(book,
                linkTo(methodOn(BookController.class).getBookByIsbn(book.getIsbn())).withSelfRel(),
                linkTo(methodOn(BookController.class).getAllBooks()).withRel("books"));
    }
}
