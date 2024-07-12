package com.nathanramiro.springtest.genre;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcGenreRepository implements GenreRepository {
    private final JdbcClient jdbcClient;

    public JdbcGenreRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<String> getAll() {
        return jdbcClient.sql("select distinct genre_name from genre")
                .query(String.class)
                .list();
    }
}
