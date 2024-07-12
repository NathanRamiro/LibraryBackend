package com.nathanramiro.springtest.book_index;

import java.util.List;
import java.util.Optional;

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

    public List<BookIndex> getByGenre(List<String> genres) {
        String sql = """
                SELECT i.index_id, i.book_name, i.isbn, i.publisher, i.writer
                FROM book_index i inner join genre g on g.index_id = i.index_id
                WHERE g.genre_name in (:?)
                GROUP BY i.index_id
                ORDER BY COUNT(i.index_id) DESC
                """;

        String inParams = "";

        for (int i = 0; i < genres.size(); i++) {
            inParams = inParams.concat("?,");
        }

        inParams = inParams.substring(0, inParams.length() - 1);

        return jdbcClient.sql(sql.replace(":?", inParams))
                .params(genres)
                .query(BookIndex.class)
                .list();
    }

}
