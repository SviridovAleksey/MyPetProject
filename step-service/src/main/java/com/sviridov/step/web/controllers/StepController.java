package com.sviridov.step.web.controllers;


import com.sviridov.rememberka.resource_server.InstructionInfoDTO;
import com.sviridov.rememberka.step_service.StepInfoDTO;
import com.sviridov.step.service.StepInfoServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/steps")
@RequiredArgsConstructor
public class StepController {

    private final StepInfoServiceImp stepInfoService;

    @PostMapping
    public List<StepInfoDTO> getSteps(@RequestBody InstructionInfoDTO infoDTO) {
        return stepInfoService.getStepsByInstInfo(infoDTO);
    }

    @PutMapping
    public StepInfoDTO saveOneStep(@RequestBody StepInfoDTO stepInfoDTO, @RequestParam("userId") Long userId) {
        return stepInfoService.saveOneStep(stepInfoDTO, userId);
    }

}
