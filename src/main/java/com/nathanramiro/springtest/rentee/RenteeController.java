package com.nathanramiro.springtest.rentee;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/rentees")
public class RenteeController {
    private final JdbcRenteeRepository jdbcRenteeRepository;

    public RenteeController(JdbcRenteeRepository jdbcRenteeRepository){
        this.jdbcRenteeRepository = jdbcRenteeRepository;
    }

    @GetMapping("")
    public List<Rentee> getAll() {

        return jdbcRenteeRepository.getAll();
    }
    
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void postNewRentee(@Valid @RequestBody List<Rentee> rentees) {
        
        jdbcRenteeRepository.postNewRentee(rentees);
    }
    
}