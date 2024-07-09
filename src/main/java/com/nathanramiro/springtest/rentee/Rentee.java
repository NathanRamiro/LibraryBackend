package com.nathanramiro.springtest.rentee;

import jakarta.validation.constraints.NotEmpty;

public record Rentee(
    Integer rentee_id,
    @NotEmpty
    String rentee_name,
    String rentee_phone,
    String rentee_email
) {
    
}
