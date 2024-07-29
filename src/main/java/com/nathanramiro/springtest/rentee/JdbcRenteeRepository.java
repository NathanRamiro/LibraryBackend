package com.nathanramiro.springtest.rentee;

import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

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
