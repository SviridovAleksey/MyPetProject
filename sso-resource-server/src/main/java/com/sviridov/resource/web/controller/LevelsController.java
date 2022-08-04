package com.sviridov.resource.web.controller;


import com.sviridov.rememberka.levels_service.LevelsDTO;
import com.sviridov.rememberka.step_service.StepInfoDTO;
import com.sviridov.resource.error_handling.InstructionsError;
import com.sviridov.resource.service.impl.users.UserServiceImp;
import com.sviridov.resource.web.feigns.FeignClientLevels;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/levels")
@RequiredArgsConstructor
public class LevelsController {

    private final UserServiceImp userServiceImp;
    private final FeignClientLevels feignClientLevels;


    @GetMapping
    public List<LevelsDTO> getAllLevels(@AuthenticationPrincipal Jwt jwt) {
        userServiceImp.checkExistUserAndSaveNew(jwt.getSubject());
        return feignClientLevels.getLevelsByUserId(userServiceImp.findUserByName(jwt.getSubject()).getId());
    }

    @PutMapping()
    public ResponseEntity<?> saveOrUpdateOneLevel(@AuthenticationPrincipal Jwt jwt, @RequestBody LevelsDTO levelsDTO) {
        userServiceImp.checkExistUserAndSaveNew(jwt.getSubject());
      //todo proverka
        return ResponseEntity.ok(feignClientLevels.saveOrUpdateOneLevel(levelsDTO,
                userServiceImp.findUserByName(jwt.getSubject()).getId()));
    }

}
