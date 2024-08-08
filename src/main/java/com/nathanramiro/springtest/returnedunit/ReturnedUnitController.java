package com.nathanramiro.springtest.returnedunit;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/returnedunits")
public class ReturnedUnitController{

    private final JdbcReturnedUnitRepository jdbcReturnedUnitRepository;

    public ReturnedUnitController(JdbcReturnedUnitRepository jdbcReturnedUnitRepository){
        this.jdbcReturnedUnitRepository = jdbcReturnedUnitRepository;
    }

    @GetMapping("")    
    public List<ReturnedUnit> getAll() {

        return jdbcReturnedUnitRepository.getAll();
    }

    @GetMapping("/find/taken")
    public ReturnedUnit getByTakenID(@RequestParam Integer taken_id) {

        Optional<ReturnedUnit> retUnit = jdbcReturnedUnitRepository.getByTakenID(taken_id);

        if (retUnit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return retUnit.get();
    }

    @GetMapping("/find/id")
    public ReturnedUnit getByID(@RequestParam Integer returned_id) {

        Optional<ReturnedUnit> retUnit = jdbcReturnedUnitRepository.getByID(returned_id);

        if (retUnit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return retUnit.get();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void postReturnedUnit(@Valid @RequestBody List<ReturnedUnit> returnedUnits){

        jdbcReturnedUnitRepository.postReturnedUnit(returnedUnits);
    }

}
