package com.bibliotech.bibliotech.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private String id;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "ISBN must not be blank")
    private String isbn;

    @NotBlank(message = "Author name must not be blank")
    private String authorName;

    @Min(value = 1, message = "Stock must be at least 1")
    private int stock;
}