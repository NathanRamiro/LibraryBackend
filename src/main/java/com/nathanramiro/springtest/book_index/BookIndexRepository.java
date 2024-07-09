package com.nathanramiro.springtest.book_index;

import java.util.List;
import java.util.Optional;

public interface BookIndexRepository {
    List<BookIndex> getAll();
    Optional<BookIndex> getByID(Integer id);
}
