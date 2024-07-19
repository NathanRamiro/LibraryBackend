package com.nathanramiro.springtest.bookindex;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/bookindex")
public class BookIndexController {
    private final JdbcBookIndexRepository jdbcBookIndexRepository;

    public BookIndexController(JdbcBookIndexRepository jdbcBookIndexRepository) {
        this.jdbcBookIndexRepository = jdbcBookIndexRepository;
    }

    @GetMapping("")
    public List<BookIndex> getAll() {
        return jdbcBookIndexRepository.getAll();
    }

    @GetMapping("/find/id")
    public BookIndex getByID(@RequestParam Integer id) {
        
        Optional<BookIndex> bkIndex = jdbcBookIndexRepository.getByID(id);
        if (bkIndex.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return bkIndex.get();
    }

    @GetMapping("/find/genre")
    public List<BookIndex> getByGenre(@RequestParam List<String> genres) {

        return jdbcBookIndexRepository.getByGenre(genres);
    }

}
