package com.nathanramiro.springtest.bookunit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookUnitRepository {
    List<BookUnit> getAll(Boolean availableOnly);

    List<BookUnit> getAllByBkIndex(Integer index_id, Boolean availableOnly);

    Optional<BookUnit> getByID(Integer unit_id);

    Optional<BookUnit> getByUUID(UUID unit_uuid);

    List<BookUnit> postNewUnit(List<Integer> index_id_List);
}
