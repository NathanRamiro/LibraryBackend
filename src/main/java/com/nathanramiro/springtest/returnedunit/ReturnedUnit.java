package com.nathanramiro.springtest.returnedunit;

import java.time.LocalDate;

public record ReturnedUnit(
        Integer returned_id,
        Integer taken_id,
        LocalDate returned_date) {

}
