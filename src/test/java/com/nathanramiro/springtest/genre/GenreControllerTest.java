package com.nathanramiro.springtest.genre;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nathanramiro.springtest.bookindex.BookIndex;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    JdbcGenreRepository repository;

    private final String basePath = "/api/genres";
    private final List<String> genres = new ArrayList<>();
    private final BookIndex bookIndex = new BookIndex(null,
            "indexNameTest",
            "1234567890000",
            "indexWriterTest",
            "indexPublisherTest");

    @BeforeEach
    void setup() {
        genres.clear();
    }

    @Test
    void testGetAll() throws Exception {
        genres.add("genreName2");
        genres.add("genreName3");
        genres.add("genreName");

        when(repository.getAll()).thenReturn(genres);
        mvc.perform(get(basePath))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(genres.size())));
    }

    @Test
    void testPostAddIndexToGenre() throws Exception {

        genres.add("genreName");

        ObjectWriter objWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mvc.perform(
                post(basePath + "/create/addto")
                        .contentType("application/json")
                        .content(objWriter.writeValueAsString(List.of(new IndexGenreComp(bookIndex, genres)))))
                .andExpect(status().isCreated());
    }

    @Test
    void testPostNewGenre() throws Exception {

        genres.add("genreNameTest");

        ObjectWriter objWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mvc.perform(
                post(basePath + "/create/genre")
                        .contentType("application/json")
                        .content(objWriter.writeValueAsString(List.of(new Genre(null, genres.get(0))))))
                .andExpect(status().isCreated());
    }
}