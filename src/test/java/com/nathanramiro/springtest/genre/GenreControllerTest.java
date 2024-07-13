package com.nathanramiro.springtest.genre;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    JdbcGenreRepository repository;

    private final List<String> genres = new ArrayList<>();
    private final String basePath = "/api/genres";

    @BeforeEach
    void setup() {
        genres.clear();
    }

    @Test
    void testGetAll() throws Exception {
        genres.add("genreName2");
        genres.add("genreName3");
        genres.add("genreName");

        when(repository.getAll()).thenReturn(genres); // testing like this feels pointless
        mvc.perform(get(basePath))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(genres.size())));
    }
}