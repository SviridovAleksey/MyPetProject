package com.sviridov.rememberka.step_service;

import java.util.Objects;

public record StepInfoDTO (Long id, String name, Long stepPlace, Long infoId, StepTextDTO stepTextDTO) {

    public StepInfoDTO{
        Objects.requireNonNull(name);
        Objects.requireNonNull(stepPlace);
        Objects.requireNonNull(infoId);
        Objects.requireNonNull(stepTextDTO);
    }

}
