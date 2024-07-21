package com.nathanramiro.springtest.genre;

import java.util.List;

public interface GenreRepository {
    List<String> getAll();

    void postNewGenre(List<Genre> genres);
}
