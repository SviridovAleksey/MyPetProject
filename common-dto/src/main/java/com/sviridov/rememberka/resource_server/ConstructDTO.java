package com.sviridov.rememberka.resource_server;

import java.util.Objects;

public record ConstructDTO (String name, String value) {
    public ConstructDTO{
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
    }
}
