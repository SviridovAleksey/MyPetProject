package com.sviridov.rememberka.resource_server;


import java.util.Objects;

public record InstructionInfoDTO (Long id, String name, String description, Long levelOwner,
                                  ConstructDTO constructDTO) {

    public InstructionInfoDTO{
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);
        Objects.requireNonNull(levelOwner);
        Objects.requireNonNull(constructDTO);
    }

}
