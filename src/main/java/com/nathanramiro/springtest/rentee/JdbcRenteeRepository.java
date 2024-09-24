package com.nathanramiro.springtest.rentee;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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

        if (rentee_phone.length() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "rentee_phone must not be empty");
        }

        String sql = """
                WITH vals(rentee_id, score) AS (
                SELECT r.rentee_id,
                    levenshtein_less_equal(r.rentee_phone, :rentee_phone,
                        50, 1, 50,50)
                from rentee r
                )
                SELECT r.*
                FROM vals v inner join rentee r
                on v.rentee_id = r.rentee_id
                WHERE v.score < 50
                ORDER BY v.score ASC
                """;

        return jdbcClient.sql(sql)
                .param("rentee_phone", rentee_phone)
                .query(Rentee.class)
                .list();
    }

    @Override
    public List<Rentee> getByEmail(String rentee_email) {

        if (rentee_email.length() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "rentee_email must not be empty");
        }

        String sql = """
                WITH vals(rentee_id, score) AS (
                SELECT r.rentee_id, levenshtein_less_equal(r.rentee_email, :rentee_email, 50, 1, 50,50)
                from rentee r
                )
                SELECT r.*
                FROM vals v inner join rentee r
                on v.rentee_id = r.rentee_id
                WHERE v.score < 50
                ORDER BY v.score asc
                """;

        return jdbcClient.sql(sql)
                .param("rentee_email", rentee_email)
                .query(Rentee.class)
                .list();
    }

    @Override
    @Transactional
    public void postNewRentee(List<Rentee> rentees) {

        HashMap<String, String> valMap = new HashMap<>();
        String params = "";

        boolean hasError = false;
        String errors = "";
        for (int i = 0; i < rentees.size(); i++) {

            Rentee currRentee = rentees.get(i);

            try {
                if (!currRentee.hasValidPhone()) {
                    hasError = true;
                    errors += "rentee[" + i + "]:invalid phone number ("
                            + currRentee.rentee_phone() + ");";
                }
            } catch (NullPointerException e) {
                // all good
            }
            try {
                if (!currRentee.hasValidEmail()) {
                    hasError = true;
                    errors += "rentee[" + i + "]:invalid e-mail ("
                            + currRentee.rentee_email() + ");";
                }
            } catch (NullPointerException e) {
                // all good
            }
            if (hasError) {
                continue;
            }

            params += "(:name" + i + ",:phone" + i + ",:email" + i + "),";
            valMap.put("name" + i, currRentee.rentee_name());
            valMap.put("phone" + i, currRentee.rentee_phone());
            valMap.put("email" + i, currRentee.rentee_email());
        }

        if (hasError) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    errors);
        }

        params = params.substring(0, params.length() - 1);

        String sql = """
                INSERT INTO rentee (rentee_name,rentee_phone,rentee_email)
                VALUES :params
                ON CONFLICT DO NOTHING
                """;

        jdbcClient.sql(sql.replace(":params", params))
                .params(valMap)
                .update();
    }
}
