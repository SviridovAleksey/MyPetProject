package com.sviridov.rememberka.levels_service;


import java.util.Objects;

public record LevelsDTO (Long id, String name, Long owner, Long levelPlace) {

    public LevelsDTO {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(owner);
        Objects.requireNonNull(levelPlace);
    }

}
