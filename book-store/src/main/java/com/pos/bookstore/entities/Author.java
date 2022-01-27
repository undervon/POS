package com.pos.bookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "Id should not be less than 1")
    private Long id;

    @NotNull(message = "First name cannot be null")
    @Size(min = 1, max = 20, message = "First name most be between 1 and 20 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 1, max = 20, message = "Last name most be between 1 and 20 characters")
    private String lastName;

    @Transient
    @OneToMany(mappedBy = "author", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<BookAuthor> bookAuthorList = new ArrayList<>();
}
