package com.nathanramiro.springtest.genre;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/find/name")
    public List<String> getByName(@RequestParam String genre_name) {

        return jdbcGenreRepository.getByName(genre_name);
    }

    @PostMapping("/create/genre")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void postNewGenre(@Valid @RequestBody List<Genre> genres) {

        jdbcGenreRepository.postNewGenre(genres);
    }

    @PostMapping("/create/addto")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void postAddIndexToGenre(@Valid @RequestBody List<IndexGenreComp> indexGenreComp) {

        for (IndexGenreComp currComp : indexGenreComp) {

            if (currComp.bookIndex().index_id() == null) {

                jdbcGenreRepository.postAddIndexToGenre(currComp);
            } else {

                jdbcGenreRepository.postAddIndexIDToGenre(
                        currComp.bookIndex().index_id(),
                        currComp.genres());
            }
        }
    }

}