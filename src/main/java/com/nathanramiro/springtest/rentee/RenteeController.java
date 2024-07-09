package com.nathanramiro.springtest.rentee;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/rentees")
public class RenteeController {
    private final JdbcRenteeRepository renteeRepository;

    public RenteeController(JdbcRenteeRepository renteeRepository){
        this.renteeRepository = renteeRepository;
    }

    @GetMapping("")
    public List<Rentee> getAll() {
        return renteeRepository.getAll();
    }
    
}
