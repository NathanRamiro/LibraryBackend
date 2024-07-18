package com.nathanramiro.springtest.takenunit;

import java.time.LocalDate;

public record TakenUnit(
    Integer taken_id,
    Integer unit_id,
    Integer rentee_id,
    LocalDate taken_date,
    LocalDate due_date
) {
    
}
