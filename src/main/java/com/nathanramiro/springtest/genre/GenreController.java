package com.nathanramiro.springtest.genre;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final JdbcGenreRepository jdbcGenreRepository;

    public GenreController(JdbcGenreRepository jdbcGenreRepository) {
        this.jdbcGenreRepository = jdbcGenreRepository;
    }

    @GetMapping("")
    public List<String> getAll() {
        return jdbcGenreRepository.getAll();
    }
}