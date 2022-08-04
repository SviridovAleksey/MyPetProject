package com.sviridov.resource.web.controller;


import com.sviridov.rememberka.step_service.StepInfoDTO;
import com.sviridov.resource.error_handling.InstructionsError;
import com.sviridov.resource.service.impl.instructions.InstructionsServiceImp;
import com.sviridov.resource.service.impl.users.UserServiceImp;
import com.sviridov.resource.web.feigns.FeignClientInstructionSteps;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/steps")
@RequiredArgsConstructor
public class StepsController {

    private final UserServiceImp userServiceImp;
    private final FeignClientInstructionSteps feignClientInstructionSteps;
    private final InstructionsServiceImp instructionsServiceImp;

    @GetMapping("/{id}")
    public List<StepInfoDTO> getStepsByInfoId(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        userServiceImp.checkExistUserAndSaveNew(jwt.getSubject());
        return feignClientInstructionSteps.getSteps(instructionsServiceImp.getInstructionDTO(id, jwt.getSubject()));
    }

    @PutMapping()
    public ResponseEntity<?> saveOneStep(@AuthenticationPrincipal Jwt jwt, @RequestBody StepInfoDTO stepInfoDTO) {
        userServiceImp.checkExistUserAndSaveNew(jwt.getSubject());
        if(!instructionsServiceImp.getInstructionDTO(stepInfoDTO.infoId(), jwt.getSubject()).id()
                .equals(stepInfoDTO.infoId()))
            return new ResponseEntity<>(new InstructionsError(HttpStatus.BAD_REQUEST.value(),
                    "No coincidence username == instruction Info"), HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(feignClientInstructionSteps.saveOneStep(stepInfoDTO,
                userServiceImp.findUserByName(jwt.getSubject()).getId()));
    }


}
