package com.nathanramiro.springtest.bookunit;

import java.util.UUID;

public record BookUnit(
    Integer unit_id,
    Integer index_id,
    Boolean available,
    UUID unit_uuid
) {

}
