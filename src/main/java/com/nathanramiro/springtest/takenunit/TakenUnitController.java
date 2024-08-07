package com.nathanramiro.springtest.takenunit;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/takenunits")
public class TakenUnitController {
    private final JdbcTakenUnitRepository jdbcTakenUnitListRepository;

    public TakenUnitController(JdbcTakenUnitRepository jdbcTakenUnitListRepository) {
        this.jdbcTakenUnitListRepository = jdbcTakenUnitListRepository;
    }

    @GetMapping("")
    public List<TakenUnit> getAll() {

        return jdbcTakenUnitListRepository.getAll();
    }

    @GetMapping("/due")
    public List<TakenUnit> getAllPastDue() {

        return jdbcTakenUnitListRepository.getAllPastDue();
    }

    @GetMapping("/find/rentee")
    public List<TakenUnit> getAllByRentee(@RequestParam Integer rentee_id) {

        return jdbcTakenUnitListRepository.getAllByRentee(rentee_id);
    }

    @GetMapping("/find/unit")
    public TakenUnit getByUnitID(@RequestParam Integer unit_id) {

        Optional<TakenUnit> tkUnit = jdbcTakenUnitListRepository.getByUnitID(unit_id);

        if (tkUnit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return tkUnit.get();
    }

    @GetMapping("/find/id")
    public TakenUnit getByID(@RequestParam Integer taken_id) {

        Optional<TakenUnit> tkUnit = jdbcTakenUnitListRepository.getByID(taken_id);

        if (tkUnit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return tkUnit.get();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void postTakenUnit(@Valid @NotEmpty @RequestBody List<TakenUnit> takenUnitList) {

        jdbcTakenUnitListRepository.postTakenUnit(takenUnitList);
    }

}
