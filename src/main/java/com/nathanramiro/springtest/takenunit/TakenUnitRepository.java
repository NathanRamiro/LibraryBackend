package com.nathanramiro.springtest.takenunit;

import java.util.List;
import java.util.Optional;

public interface TakenUnitRepository {
    List<TakenUnit> getAll();

    List<TakenUnit> getAllPastDue();

    List<TakenUnit> getAllByRentee(Integer rentee_id);

    Optional<TakenUnit> getByUnitID(Integer unit_id);

    Optional<TakenUnit> getByID(Integer taken_id);
}
