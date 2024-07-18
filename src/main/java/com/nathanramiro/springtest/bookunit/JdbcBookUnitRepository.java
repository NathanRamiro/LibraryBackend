package com.nathanramiro.springtest.bookunit;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcBookUnitRepository implements BookUnitRepository {

    private final JdbcClient jdbcClient;

    public JdbcBookUnitRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<BookUnit> getAll(Boolean availableOnly) {
        return jdbcClient.sql("SELECT * from book_unit" +
                (availableOnly ? " where available = true" : ""))
                .query(BookUnit.class)
                .list();
    }

    @Override
    public List<BookUnit> getAllByBkIndex(Integer index_id, Boolean availableOnly) {

        return jdbcClient.sql("SELECT * from book_unit where index_id = :index_id" +
                (availableOnly ? " and available = true" : ""))
                .param("index_id", index_id)
                .query(BookUnit.class)
                .list();
    }

    @Override
    public Optional<BookUnit> getByID(Integer unit_id) {

        return jdbcClient.sql("SELECT * from book_unit where unit_id = :unit_id")
                .param("unit_id", unit_id)
                .query(BookUnit.class)
                .optional();
    }

}
