package com.sviridov.resource.web.feigns;


import com.sviridov.rememberka.levels_service.LevelsDTO;
import com.sviridov.rememberka.step_service.StepInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "${eureka.feign.levels-service-name}")
public interface FeignClientLevels {

    @GetMapping(value = "levels-server/api/v1/levels/{userId}", consumes = "application/json")
    List<LevelsDTO> getLevelsByUserId(@PathVariable("userId") Long userId);

    @PutMapping(value = "levels-server/api/v1/levels")
    LevelsDTO saveOrUpdateOneLevel(@RequestBody LevelsDTO levelsDTO, @RequestParam("userId") Long userId);
}
