package com.nathanramiro.springtest.rentee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RenteeRepository {
    List<Rentee> getAll();

    Optional<Rentee> getByID(Integer rentee_id);

    Optional<Rentee> getByUUID(UUID rentee_uuid);

    List<Rentee> getByPhone(String rentee_phone);

    List<Rentee> getByEmail(String rentee_email);

    void postNewRentee(List<Rentee> rentees);
}
