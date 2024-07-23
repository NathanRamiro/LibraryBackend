package com.nathanramiro.springtest.bookindex;

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

    @Override
    public List<BookIndex> getAll() {
        return jdbcClient.sql("select * from book_index")
                .query(BookIndex.class)
                .list();
    }

    @Override
    public Optional<BookIndex> getByID(Integer id) {
        return jdbcClient.sql("select * from book_index where index_id = :id")
                .param("id", id)
                .query(BookIndex.class)
                .optional();
    }

    @Override
    public List<BookIndex> getByGenre(List<String> genres) {
        String sql = """
                SELECT bi.*
                FROM book_index bi
                INNER JOIN genre_to_index_map gtim ON bi.index_id = gtim.index_id
                inner join genre g ON gtim.genre_id = g.genre_id
                WHERE g.genre_name IN (:?)
                GROUP BY bi.index_id
                ORDER BY count(bi.index_id) DESC
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
