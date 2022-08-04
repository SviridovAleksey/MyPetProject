package com.sviridov.resource.web.controller;


import com.sviridov.rememberka.resource_server.InstructionInfoDTO;
import com.sviridov.resource.error_handling.InstructionsError;
import com.sviridov.resource.error_handling.ResourceNotFoundException;
import com.sviridov.resource.service.impl.instructions.InstructionsServiceImp;
import com.sviridov.resource.service.impl.users.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/instructions")
@RequiredArgsConstructor
public class InstructionController {

    private final InstructionsServiceImp instructionsServiceImp;
    private final UserServiceImp userServiceImp;

    @GetMapping
    public List<InstructionInfoDTO> findAllInstruction(@AuthenticationPrincipal Jwt jwt) {
        userServiceImp.checkExistUserAndSaveNew(jwt.getSubject());
        return instructionsServiceImp.getAllInstructionByUserName(jwt.getSubject());
    }

    @GetMapping("/{id}")
    public InstructionInfoDTO findInstructionBy(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        userServiceImp.checkExistUserAndSaveNew(jwt.getSubject());
        return instructionsServiceImp.getInstructionDTO(id, jwt.getSubject());
    }

    @GetMapping("/folder/{id}")
    public List<InstructionInfoDTO> findInstructionsByIdFolder(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        userServiceImp.checkExistUserAndSaveNew(jwt.getSubject());
        return instructionsServiceImp.getAllInstructionByUserNameAndIdFolder(jwt.getSubject(), id);
    }

    @PutMapping
    public InstructionInfoDTO addNewInstruction(@AuthenticationPrincipal Jwt jwt,
                                                @RequestBody InstructionInfoDTO infoDTO ) {
        userServiceImp.checkExistUserAndSaveNew(jwt.getSubject());
        return instructionsServiceImp.saveNewInstructionInfo(jwt.getSubject(), infoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInstruction(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        userServiceImp.checkExistUserAndSaveNew(jwt.getSubject());
        try {
            instructionsServiceImp.deleteInstruction(id, jwt.getSubject());
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new InstructionsError(HttpStatus.BAD_REQUEST.value(),
                    e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
