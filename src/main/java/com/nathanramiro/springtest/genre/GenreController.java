package com.nathanramiro.springtest.genre;

import org.springframework.web.bind.annotation.RestController;

import com.nathanramiro.springtest.book_index.BookIndex;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
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

    @GetMapping("/find")//TODO: findByGenre should be on BookIndex, also add bad request at genre.lenght==0
    public List<BookIndex> getByGenre(@RequestBody String[] genres){

        return jdbcGenreRepository.getBookByGenre(Arrays.asList(genres));
    }
    

}
