package com.nathanramiro.springtest.bookindex;

import java.util.List;
import java.util.Optional;

public interface BookIndexRepository {
    List<BookIndex> getAll();

    Optional<BookIndex> getByID(Integer id);

    List<BookIndex> getByGenre(List<String> genres);
}
