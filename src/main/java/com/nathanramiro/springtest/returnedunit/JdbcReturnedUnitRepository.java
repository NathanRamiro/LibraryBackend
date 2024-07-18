package com.nathanramiro.springtest.returnedunit;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcReturnedUnitRepository implements ReturnedUnitRepository {

    private final JdbcClient jdbcClient;

    public JdbcReturnedUnitRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<ReturnedUnit> getAll() {

        return jdbcClient.sql("SELECT * from returned_list")
                .query(ReturnedUnit.class)
                .list();
    }

    @Override
    public Optional<ReturnedUnit> getByTakenID(Integer taken_id) {

        return jdbcClient.sql("SELECT * from returned_list where taken_id = :taken_id")
                .param("taken_id", taken_id)
                .query(ReturnedUnit.class)
                .optional();
    }

    @Override
    public Optional<ReturnedUnit> getByID(Integer returned_id) {

        return jdbcClient.sql("SELECT * from returned_list where returned_id = :returned_id")
                .param("returned_id", returned_id)
                .query(ReturnedUnit.class)
                .optional();
    }

}
