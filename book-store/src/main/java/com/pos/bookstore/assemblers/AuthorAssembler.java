package com.pos.bookstore.assemblers;

import com.pos.bookstore.controllers.AuthorController;
import com.pos.bookstore.models.AuthorDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorAssembler implements RepresentationModelAssembler<AuthorDTO, EntityModel<AuthorDTO>> {

    @Override
    public @NotNull EntityModel<AuthorDTO> toModel(@NotNull AuthorDTO author) {
        return EntityModel.of(author,
                linkTo(methodOn(AuthorController.class).getAuthorById(author.getId())).withSelfRel(),
                linkTo(methodOn(AuthorController.class).getAllAuthors()).withRel("authors"));
    }
}
