package com.nathanramiro.springtest.genre;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcGenreRepository implements GenreRepository {
    private final JdbcClient jdbcClient;

    public JdbcGenreRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<String> getAll() {
        return jdbcClient.sql("SELECT DISTINCT genre_name FROM genre ORDER BY genre_name ASC")
                .query(String.class)
                .list();
    }

    @Override
    public void postNewGenre(List<Genre> genres) {

        String sql = """
                INSERT INTO genre (genre_name,index_id)
                VALUES
                """;

        for (Genre genre : genres) {
            sql += " (?," + genre.index_id() + "),";
        }

        sql = sql.substring(0, sql.length() - 1);

        List<String> params = new ArrayList<>();

        for (Genre genre : genres) {
            params.add(genre.genre_name());
        }

        jdbcClient.sql(sql)
                .params(params)
                .update();
    }
}
