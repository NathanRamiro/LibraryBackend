package com.nathanramiro.springtest.bookunit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public Optional<BookUnit> getByUUID(UUID unit_uuid) {

        return jdbcClient.sql("SELECT * FROM book_unit bu WHERE bu.unit_uuid = :unit_uuid")
                .param("unit_uuid", unit_uuid)
                .query(BookUnit.class)
                .optional();
    }

    @Override
    @Transactional
    public List<BookUnit> postNewUnit(List<Integer> index_id_List) {

        if (index_id_List.size() == 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "request must not be empty");
        }

        String vals = "";
        for (Integer currInt : index_id_List) {

            if (currInt == null) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "request must not include Null values");
            }
            vals += "(" + currInt + "),";
        }
        vals = vals.substring(0, vals.length() - 1);

        String sql = """
                INSERT INTO book_unit (index_id)
                VALUES :vals
                RETURNING *
                """;

        return jdbcClient.sql(sql.replace(":vals", vals))
                .query(BookUnit.class)
                .list();

    }

}
