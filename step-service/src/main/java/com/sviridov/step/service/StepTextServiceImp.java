package com.sviridov.step.service;


import com.sviridov.step.persistence.model.StepText;
import com.sviridov.step.persistence.repository.StepTextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StepTextServiceImp {

    private final StepTextRepository stepTextRepository;


    public StepText saveStepText(StepText stepText) {
        return stepTextRepository.save(stepText);
    }

}
