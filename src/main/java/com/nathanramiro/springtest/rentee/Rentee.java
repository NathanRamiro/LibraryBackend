package com.nathanramiro.springtest.rentee;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record Rentee(
    Integer rentee_id,
    @NotEmpty
    String rentee_name,
    @Size(min = 11,max = 11)
    String rentee_phone,
    String rentee_email
) {
    
}
