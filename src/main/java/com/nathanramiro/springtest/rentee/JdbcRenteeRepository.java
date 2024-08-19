package com.nathanramiro.springtest.rentee;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class JdbcRenteeRepository implements RenteeRepository {

    private final JdbcClient jdbcClient;

    public JdbcRenteeRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Rentee> getAll() {

        return jdbcClient.sql("select * from rentee")
                .query(Rentee.class)
                .list();
    }

    @Override
    public Optional<Rentee> getByID(Integer rentee_id) {

        return jdbcClient.sql("SELECT * FROM rentee r WHERE r.rentee_id = :rentee_id")
                .param("rentee_id", rentee_id)
                .query(Rentee.class)
                .optional();
    }

    @Override
    public Optional<Rentee> getByUUID(UUID rentee_uuid) {

        return jdbcClient.sql("SELECT * FROM rentee r WHERE r.rentee_uuid = :rentee_uuid")
                .param("rentee_uuid", rentee_uuid)
                .query(Rentee.class)
                .optional();
    }

    @Override
    public List<Rentee> getByPhone(String rentee_phone) {

        if (rentee_phone.length()==0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "rentee_phone must not be empty"
            );
        }

        String sql = """
                SELECT r.*
                FROM rentee r
                ORDER BY levenshtein(r.rentee_phone, :rentee_phone ,5,1,5) ASC
                LIMIT 5
                """;

        return jdbcClient.sql(sql)
                .param("rentee_phone", rentee_phone)
                .query(Rentee.class)
                .list();
    }

    @Override
    public List<Rentee> getByEmail(String rentee_email) {

        if (rentee_email.length()==0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "rentee_email must not be empty"
            );
        }

        String sql = """
                SELECT r.*
                FROM rentee r
                ORDER BY levenshtein(r.rentee_email, :rentee_email ,5,1,5) ASC
                LIMIT 5
                """;

        return jdbcClient.sql(sql)
                .param("rentee_email", rentee_email)
                .query(Rentee.class)
                .list();
    }

    @Override
    public void postNewRentee(List<Rentee> rentees) {

        String sql = """
                INSERT INTO rentee (rentee_name,rentee_phone,rentee_email)
                VALUES
                """;

        HashMap<String, String> valMap = new HashMap<>();

        for (int i = 0; i < rentees.size(); i++) {
            sql += " (:name" + i + ",:phone" + i + ",:email" + i + "),";
            valMap.put("name" + i, rentees.get(i).rentee_name());
            valMap.put("phone" + i, rentees.get(i).rentee_phone());
            valMap.put("email" + i, rentees.get(i).rentee_email());
        }

        sql = sql.substring(0, sql.length() - 1) + " ON CONFLICT DO NOTHING";

        jdbcClient.sql(sql)
                .params(valMap)
                .update();
    }
}
