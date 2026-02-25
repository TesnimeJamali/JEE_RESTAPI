package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.Tp2Application;
import com.bibliotech.bibliotech.dto.BookDTO;
import com.bibliotech.bibliotech.repository.BookRepository;
import com.bibliotech.bibliotech.security.SecurityTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Tp2Application.class)
@AutoConfigureMockMvc
@Import(SecurityTestConfig.class) // ← on importe la config sécurité pour tests
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldCreateBook() throws Exception {

        BookDTO dto = new BookDTO(
                null,
                "Clean Code",
                "123456",
                "Robert Martin",
                5
        );

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    void shouldGetAllBooks() throws Exception {

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk());
    }
}