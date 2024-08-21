package com.nathanramiro.springtest.rentee;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record Rentee(
        Integer rentee_id,
        @NotEmpty String rentee_name,
        @Size(min = 11, max = 11) String rentee_phone,
        String rentee_email,
        UUID rentee_uuid) {

    public boolean hasValidPhone() {
        if (rentee_phone == null) {
            throw new NullPointerException();
        }
        return rentee_phone.matches("[0-9]{11}");
    }

    public boolean hasValidEmail() {
        if (rentee_email == null) {
            throw new NullPointerException();
        }
        return rentee_email.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
    }
}
