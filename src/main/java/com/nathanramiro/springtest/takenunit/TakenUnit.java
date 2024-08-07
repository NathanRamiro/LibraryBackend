package com.nathanramiro.springtest.takenunit;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record TakenUnit(
    Integer taken_id,
    @NotNull
    Integer unit_id,
    @NotNull
    Integer rentee_id,
    @NotNull
    LocalDate taken_date,
    @NotNull
    LocalDate due_date
) {
    
}
