package com.nathanramiro.springtest.rentee;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRenteeRepository implements RenteeRepository {

    private final JdbcClient jdbcClient;

    public JdbcRenteeRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Rentee> getAll() {
        return jdbcClient.sql("select * from rentee")
                .query(Rentee.class)
                .list();
    }
}
