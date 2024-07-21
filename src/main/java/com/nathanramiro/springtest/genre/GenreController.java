package com.nathanramiro.springtest.genre;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void postNewGenre(@Valid @RequestBody List<Genre> genres) {

        jdbcGenreRepository.postNewGenre(genres);
    }

}