package com.nathanramiro.springtest.book_unit;

import java.util.List;
import java.util.Optional;

public interface BookUnitRepository {
    List<BookUnit> getAll(Boolean availableOnly);

    List<BookUnit> getAllByBkIndex(Integer index_id, Boolean availableOnly);

    Optional<BookUnit> getByID(Integer unit_id);
}
