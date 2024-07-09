package com.nathanramiro.springtest.genre;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.nathanramiro.springtest.book_index.BookIndex;

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

    public List<BookIndex> getBookByGenre(List<String> genres) {

        String sql = """
                SELECT i.book_name
                FROM book_index i inner join genre g
                on g.index_id = i.index_id
                WHERE g.genre_name in (:?)
                GROUP BY i.book_name
                ORDER BY COUNT(i.index_id) DESC;
                """;

        String inParams = "";

        for (short i = 0; i < genres.size(); i++) {
            inParams.concat("?,");
        }
        
        inParams = inParams.substring(0, inParams.length()-1);

        return jdbcClient.sql(sql.replace(":?", inParams))
                .params(genres)
                .query(BookIndex.class)
                .list();
    }
}
