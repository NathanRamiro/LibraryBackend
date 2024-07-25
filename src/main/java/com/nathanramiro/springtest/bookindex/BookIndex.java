package com.nathanramiro.springtest.bookindex;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookIndex(
    Integer index_id,
    @NotEmpty
    String book_name,
    @NotNull
    String isbn,
    @NotNull
    String writer,
    @NotNull
    String publisher
) {
    
}
