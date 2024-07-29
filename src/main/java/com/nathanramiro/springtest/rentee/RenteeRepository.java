package com.nathanramiro.springtest.rentee;

import java.util.List;

public interface RenteeRepository {
    List<Rentee> getAll();

    void postNewRentee(List<Rentee> rentees);
}
