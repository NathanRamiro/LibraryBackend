package com.nathanramiro.springtest.bookindex;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookIndex(
    Integer index_id,
    @NotEmpty
    String book_name,
    @NotNull
    @Size(min = 13, max = 13)
    String isbn,
    @NotNull
    String writer,
    @NotNull
    String publisher
) {
    
}
