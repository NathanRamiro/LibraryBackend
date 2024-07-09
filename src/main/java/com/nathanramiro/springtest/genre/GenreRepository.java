package com.nathanramiro.springtest.genre;

import java.util.List;

import com.nathanramiro.springtest.book_index.BookIndex;

public interface GenreRepository {
    public List<String> getAll();

    public List<BookIndex> getBookByGenre(List<String> genres);
}
