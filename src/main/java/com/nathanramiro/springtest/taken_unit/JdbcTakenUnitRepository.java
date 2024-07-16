package com.nathanramiro.springtest.taken_unit;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTakenUnitRepository implements TakenUnitRepository {
    private final JdbcClient jdbcClient;

    public JdbcTakenUnitRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<TakenUnit> getAll() {

        return jdbcClient.sql("SELECT * from taken_unit_list")
                .query(TakenUnit.class)
                .list();
    }

    @Override
    public List<TakenUnit> getAllPastDue() {

        return jdbcClient.sql("SELECT * from taken_unit_list WHERE due_date < CURRENT_DATE")
                .query(TakenUnit.class)
                .list();
    }

    @Override
    public List<TakenUnit> getAllByRentee(Integer rentee_id) {

        return jdbcClient.sql("Select * from taken_unit_list where rentee_id = :rentee_id")
                .param("rentee_id", rentee_id)
                .query(TakenUnit.class)
                .list();
    }

    @Override
    public Optional<TakenUnit> getByUnitID(Integer unit_id) {

        return jdbcClient.sql("Select * from taken_unit_list where unit_id = :unit_id")
                .param("unit_id", unit_id)
                .query(TakenUnit.class)
                .optional();
    }

    @Override
    public Optional<TakenUnit> getByID(Integer taken_id) {

        return jdbcClient.sql("Select * from taken_unit_list where taken_id = :taken_id")
                .param("taken_id", taken_id)
                .query(TakenUnit.class)
                .optional();
    }

}
