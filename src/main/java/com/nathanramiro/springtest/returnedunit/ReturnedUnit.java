package com.nathanramiro.springtest.returnedunit;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record ReturnedUnit(
        Integer returned_id,
        @NotNull
        Integer taken_id,
        @NotNull
        LocalDate returned_date
) {

}
