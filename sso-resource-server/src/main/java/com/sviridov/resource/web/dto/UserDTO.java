package com.sviridov.resource.web.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public record UserDTO (

        @Min(value = 1, message = "Min id = 1")
        Long id,

        @Size(min = 4, max = 255, message = "Name size: 4-255")
        String name
) { }
