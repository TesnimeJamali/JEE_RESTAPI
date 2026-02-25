package com.bibliotech.bibliotech.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "books")
public class BookDocument {

    @Id
    private String id;
    private String isbn;
    private String title;
    private String authorName;
}
