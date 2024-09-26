package com.nathanramiro.springtest.returnedunit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class JdbcReturnedUnitRepository implements ReturnedUnitRepository {

    private final JdbcClient jdbcClient;

    public JdbcReturnedUnitRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<ReturnedUnit> getAll() {

        return jdbcClient.sql("SELECT * from returned_unit")
                .query(ReturnedUnit.class)
                .list();
    }

    @Override
    public Optional<ReturnedUnit> getByTakenID(Integer taken_id) {

        return jdbcClient.sql("SELECT * from returned_unit where taken_id = :taken_id")
                .param("taken_id", taken_id)
                .query(ReturnedUnit.class)
                .optional();
    }

    @Override
    public Optional<ReturnedUnit> getByID(Integer returned_id) {

        return jdbcClient.sql("SELECT * from returned_unit where returned_id = :returned_id")
                .param("returned_id", returned_id)
                .query(ReturnedUnit.class)
                .optional();
    }

    @Override
    @Transactional
    public void postReturnedUnit(List<ReturnedUnit> returnedUnits) {

        if (returnedUnits.size() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "request must not be empty");
        }

        HashMap<String, String> paramMap = new HashMap<>();
        HashSet<Integer> dupIDs = new HashSet<>();
        for (int i = 0; i < returnedUnits.size(); i++) {

            if (returnedUnits.get(i) == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "request must not contain null itens");
            }

            if (!dupIDs.add(returnedUnits.get(i).taken_id())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "request must not contain duplicate IDs" + returnedUnits.get(i).taken_id());
            }

            paramMap.put("taken_id" + i, returnedUnits.get(i).taken_id().toString());
            paramMap.put("returned_date" + i, returnedUnits.get(i).returned_date().toString());
        }

        String sql = """
                INSERT INTO returned_unit (taken_id,returned_date)
                VALUES :params
                """;

        String params = "";
        for (int i = 0; i < returnedUnits.size(); i++) {
            params += "(:taken_id" + i + "::integer"
                    + ",:returned_date" + i + "::date),";
        }
        params = params.substring(0, params.length() - 1);

        try {

            jdbcClient.sql(sql.replace(":params", params))
                    .params(paramMap)
                    .update();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "the request contains invalid data");
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "an unknown error has occured");
        }
    }

}
