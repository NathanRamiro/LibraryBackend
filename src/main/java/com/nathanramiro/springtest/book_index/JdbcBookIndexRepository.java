package com.nathanramiro.springtest.book_index;

import java.util.List;
import java.util.*;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcBookIndexRepository implements BookIndexRepository {

    private final JdbcClient jdbcClient;

    public JdbcBookIndexRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<BookIndex> getAll() {
        return jdbcClient.sql("select * from book_index")
                .query(BookIndex.class)
                .list();
    }

    public Optional<BookIndex> getByID(Integer id) {
        return jdbcClient.sql("select * from book_index where index_id = :id")
                .param("id", id)
                .query(BookIndex.class)
                .optional();
    }

}
