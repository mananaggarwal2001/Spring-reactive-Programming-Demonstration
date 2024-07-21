package com.mananluvtocode.Recipie.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.UUID;

@Getter
@Setter
//@EqualsAndHashCode(exclude = {"recipe"})
public class Notes {
    @Id
    private String id= UUID.randomUUID().toString();

    @DBRef
    private Recipe recipe;

    // this annotation is used for the large objects to tell the database that this can exceed more than 255 characters.
    private String description;
}
