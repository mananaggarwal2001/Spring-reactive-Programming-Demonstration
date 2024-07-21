package com.mananluvtocode.Recipie.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "unitOfMeasure")
public class UnitOfMeasure {
    @Id
    private String id = UUID.randomUUID().toString();
    private String description;

}
