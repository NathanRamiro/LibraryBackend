package com.nathanramiro.springtest.genre;

import java.util.List;

import com.nathanramiro.springtest.bookindex.BookIndex;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record IndexGenreComp(
    @Valid
    @NotNull
    BookIndex bookIndex,
    @NotEmpty
    List<String> genres
) {
    
}
