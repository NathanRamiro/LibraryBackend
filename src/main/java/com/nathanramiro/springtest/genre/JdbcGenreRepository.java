package com.nathanramiro.springtest.genre;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    public List<String> getByName(String genre_name) {

        if (genre_name.length() < 2) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "genre_name below minimum length");
        }

        String sql = """
                WITH vals(genre_name,score) AS (
                SELECT g.genre_name, levenshtein_less_equal(g.genre_name , :genre_name , 50, 1, 50,50)
                FROM genre g
                )
                SELECT v.genre_name
                FROM vals v
                WHERE v.score < 50
                ORDER BY v.score ASC
                """;

        return jdbcClient.sql(sql)
                .param("genre_name", genre_name)
                .query(String.class)
                .list();
    }

    @Override
    @Transactional
    public void postNewGenre(List<Genre> genres) {

        String sql = """
                INSERT INTO genre (genre_name)
                VALUES :params
                on conflict (genre_name) do nothing
                """;

        String params = "";
        HashMap<String, String> valMap = new HashMap<>(genres.size());

        for (int i = 0; i < genres.size(); i++) {

            params += "(:genre_name" + i + "),";
            valMap.put("genre_name" + i, genres.get(i).genre_name());
        }
        params = params.substring(0, params.length() - 1);

        jdbcClient.sql(sql.replace(":params", params))
                .params(valMap)
                .update();
    }

    @Override
    @Transactional
    public void postAddIndexToGenre(IndexGenreComp indexGenreComp) {

        String sql = """
                INSERT INTO genre_to_index_map (index_id,genre_id)
                SELECT bi.index_id, g.genre_id
                FROM book_index bi,genre g
                WHERE (bi.book_name,bi.isbn,bi.writer,bi.publisher) = (:book_name,:isbn,:writer,:publisher)
                AND g.genre_name IN (:inParams)
                ON CONFLICT DO NOTHING
                """;

        jdbcClient.sql(sql)
                .param("book_name", indexGenreComp.bookIndex().book_name())
                .param("isbn", indexGenreComp.bookIndex().isbn())
                .param("writer", indexGenreComp.bookIndex().writer())
                .param("publisher", indexGenreComp.bookIndex().publisher())
                .param("inParams", indexGenreComp.genres())
                .update();
    }
}
