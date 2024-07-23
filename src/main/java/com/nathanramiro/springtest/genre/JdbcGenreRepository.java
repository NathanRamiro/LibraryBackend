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
        return jdbcClient.sql("SELECT genre_name FROM genre ORDER BY genre_name ASC")
                .query(String.class)
                .list();
    }

    @Override
    public void postNewGenre(List<Genre> genres) {

        String sql = """
                INSERT INTO genre (genre_name)
                VALUES
                """;

        for (int i = 0; i < genres.size(); i++) {
            sql += " (?),";
        }

        sql = sql.substring(0, sql.length() - 1);

        sql += " on conflict (genre_name) do nothing";
        
        List<String> params = new ArrayList<>();

        for (Genre genre : genres) {
            params.add(genre.genre_name());
        }

        jdbcClient.sql(sql)
                .params(params)
                .update();
    }
}
