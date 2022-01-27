package com.pos.bookstore.assemblers;

import com.pos.bookstore.controllers.BookController;
import com.pos.bookstore.models.BookVerboseDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookVerboseAssambler implements RepresentationModelAssembler<BookVerboseDTO, EntityModel<BookVerboseDTO>> {

    @Override
    public @NotNull EntityModel<BookVerboseDTO> toModel(@NotNull BookVerboseDTO bookVerbose) {
        return EntityModel.of(bookVerbose,
                linkTo(methodOn(BookController.class).getBookByIsbn(bookVerbose.getIsbn())).withSelfRel(),
                linkTo(methodOn(BookController.class).getAllBooks()).withRel("books"));
    }
}
