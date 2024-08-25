package com.nathanramiro.springtest.genre;

import java.util.List;

public interface GenreRepository {
    List<String> getAll();

    List<String> getByName(String genre_name);

    void postNewGenre(List<Genre> genres);

    void postAddIndexToGenre(IndexGenreComp indexGenreComp);
}
