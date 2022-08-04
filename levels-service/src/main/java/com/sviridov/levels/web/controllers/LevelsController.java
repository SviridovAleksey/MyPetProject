package com.sviridov.levels.web.controllers;



import com.sviridov.levels.service.LevelsInfoServiceImp;
import com.sviridov.rememberka.levels_service.LevelsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/levels")
@RequiredArgsConstructor
public class LevelsController {

    private final LevelsInfoServiceImp levelsInfoServiceImp;


    @GetMapping("/{userId}")
    public List<LevelsDTO> getLevelsByUserId(@PathVariable Long userId) {
        return levelsInfoServiceImp.getAllLevelInfoByUserId(userId);
    }

    @PutMapping
    public LevelsDTO saveOrUpdateOneLevel(@RequestBody LevelsDTO levelsDTO, @RequestParam("userId") Long userId) {
        return levelsInfoServiceImp.saveOrUpdateLevel(levelsDTO, userId);
    }


}
