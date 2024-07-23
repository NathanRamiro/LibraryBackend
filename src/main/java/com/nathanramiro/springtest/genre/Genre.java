package com.nathanramiro.springtest.genre;

import jakarta.validation.constraints.NotEmpty;

public record Genre(
    Integer genre_id,
    @NotEmpty
    String genre_name
) {

}
