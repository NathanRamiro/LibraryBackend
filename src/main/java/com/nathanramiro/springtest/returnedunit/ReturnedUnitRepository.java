package com.nathanramiro.springtest.returnedunit;

import java.util.List;
import java.util.Optional;

public interface ReturnedUnitRepository {
    List<ReturnedUnit> getAll();

    Optional<ReturnedUnit> getByTakenID(Integer taken_id);

    Optional<ReturnedUnit> getByID(Integer returned_id);

    void postReturnedUnit(List<ReturnedUnit> returnedUnits);
}
