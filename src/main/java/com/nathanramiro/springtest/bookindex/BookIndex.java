package com.nathanramiro.springtest.bookindex;

import jakarta.validation.constraints.NotEmpty;

public record BookIndex(
    Integer index_id,
    @NotEmpty
    String book_name,
    String isbn,
    String writer,
    String publisher
) {
    
}
