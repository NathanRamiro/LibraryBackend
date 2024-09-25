package com.nathanramiro.springtest.takenunit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class JdbcTakenUnitRepository implements TakenUnitRepository {
    private final JdbcClient jdbcClient;

    public JdbcTakenUnitRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<TakenUnit> getAll() {

        return jdbcClient.sql("SELECT * from taken_unit")
                .query(TakenUnit.class)
                .list();
    }

    @Override
    public List<TakenUnit> getAllPastDue() {

        return jdbcClient.sql("""
                SELECT t.*
                from taken_unit t left JOIN returned_unit r
                on t.taken_id = r.taken_id
                WHERE due_date < CURRENT_DATE AND r.taken_id is NULL
                """)
                .query(TakenUnit.class)
                .list();
    }

    @Override
    public List<TakenUnit> getAllByRentee(Integer rentee_id) {

        return jdbcClient.sql("Select * from taken_unit where rentee_id = :rentee_id")
                .param("rentee_id", rentee_id)
                .query(TakenUnit.class)
                .list();
    }

    @Override
    public Optional<TakenUnit> getByUnitID(Integer unit_id) {

        return jdbcClient.sql("Select * from taken_unit where unit_id = :unit_id")
                .param("unit_id", unit_id)
                .query(TakenUnit.class)
                .optional();
    }

    @Override
    public Optional<TakenUnit> getByID(Integer taken_id) {

        return jdbcClient.sql("Select * from taken_unit where taken_id = :taken_id")
                .param("taken_id", taken_id)
                .query(TakenUnit.class)
                .optional();
    }

    @Override
    @Transactional
    public void postTakenUnit(List<TakenUnit> takenUnitList) {

        if (takenUnitList.size() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "request must not be empty");
        }

        HashMap<String, String> paramMap = new HashMap<>();
        for (int i = 0; i < takenUnitList.size(); i++) {
            paramMap.put("unit_id" + i, takenUnitList.get(i).unit_id().toString());
            paramMap.put("rentee_id" + i, takenUnitList.get(i).rentee_id().toString());
            paramMap.put("taken_date" + i, takenUnitList.get(i).taken_date().toString());
            paramMap.put("due_date" + i, takenUnitList.get(i).due_date().toString());
        }

        HashSet<Integer> dupUnits = new HashSet<>();
        for (int i = 0; i < takenUnitList.size(); i++) {
            if (!dupUnits.add(takenUnitList.get(i).unit_id())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "request must not contain duplicate units:"
                                + takenUnitList.get(i).unit_id());
            }
        }

        String validBooksSql = """
                WITH vals(unit_id)
                AS (
                VALUES :params
                )
                SELECT v.unit_id
                FROM vals v
                INNER JOIN book_unit bu ON
                v.unit_id = bu.unit_id
                WHERE bu.available = FALSE
                """;

        String validBKParams = "";
        for (int i = 0; i < takenUnitList.size(); i++) {
            validBKParams += "(:unit_id" + i + "::integer),";
        }
        validBKParams = validBKParams.substring(0, validBKParams.length() - 1);

        List<Integer> notAvail = jdbcClient.sql(validBooksSql.replace(":params", validBKParams))
                .params(paramMap)
                .query(Integer.class)
                .list();

        if (notAvail.size() > 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "some units are marked as already taken:" + notAvail.toString());
        }

        String sql = """
                INSERT INTO taken_unit (unit_id,rentee_id,taken_date,due_date)
                VALUES :params
                """;

        String params = "";
        for (int i = 0; i < takenUnitList.size(); i++) {
            params += "(:unit_id" + i + "::integer"
                    + ",:rentee_id" + i + "::integer"
                    + ",:taken_date" + i + "::date"
                    + ",:due_date" + i + "::date),";
        }
        params = params.substring(0, params.length() - 1);

        jdbcClient.sql(sql.replace(":params", params))
                .params(paramMap)
                .update();
    }
}
