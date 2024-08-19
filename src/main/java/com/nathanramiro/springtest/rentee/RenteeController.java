package com.nathanramiro.springtest.rentee;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



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

    @GetMapping("/find/id")
    public Rentee getByID(@RequestParam Integer rentee_id) {

        Optional<Rentee> rentee = jdbcRenteeRepository.getByID(rentee_id);

        if (rentee.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
            );
        }
        return rentee.orElseThrow();
    }

    @GetMapping("/find/uuid")
    public Rentee getByUUID(@RequestParam UUID rentee_uuid) {

        Optional<Rentee> rentee = jdbcRenteeRepository.getByUUID(rentee_uuid);

        if (rentee.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
            );
        }
        return rentee.orElseThrow();
    }

    @GetMapping("/find/phone")
    public List<Rentee> getByPhone(@RequestParam String rentee_phone){

        return jdbcRenteeRepository.getByPhone(rentee_phone);
    }

    @GetMapping("/find/email")
    public List<Rentee> getByEmail(@RequestParam String rentee_email){

        return jdbcRenteeRepository.getByEmail(rentee_email);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void postNewRentee(@Valid @RequestBody List<Rentee> rentees) {
        
        jdbcRenteeRepository.postNewRentee(rentees);
    }

}