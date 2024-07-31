package com.nathanramiro.springtest.bookunit;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/bookunits")
public class BookUnitController {
    private final JdbcBookUnitRepository jdbcBookUnitRepository;

    public BookUnitController(JdbcBookUnitRepository jdbcBookUnitRepository) {
        this.jdbcBookUnitRepository = jdbcBookUnitRepository;
    }

    @GetMapping("")
    public List<BookUnit> getAll(@RequestParam(defaultValue = "false") Boolean availableOnly) {
        return jdbcBookUnitRepository.getAll(availableOnly);
    }

    @GetMapping("/find/bkindex")
    public List<BookUnit> getAllByBkIndex(
            @RequestParam Integer index_id,
            @RequestParam(defaultValue = "false") Boolean availableOnly) {

        return jdbcBookUnitRepository.getAllByBkIndex(index_id, availableOnly);
    }

    @GetMapping("/find/id")
    public BookUnit getByID(@RequestParam Integer unit_id) {
        Optional<BookUnit> bkUnit = jdbcBookUnitRepository.getByID(unit_id);

        if (bkUnit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return bkUnit.get();
    }

    @PostMapping("/create")
    public List<BookUnit> postNewUnit(@RequestBody List<Integer> index_id_List) {

        return jdbcBookUnitRepository.postNewUnit(index_id_List);
    }
}
