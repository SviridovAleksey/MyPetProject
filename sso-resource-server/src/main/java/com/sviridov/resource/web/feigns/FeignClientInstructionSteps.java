package com.sviridov.resource.web.feigns;


import com.sviridov.rememberka.resource_server.InstructionInfoDTO;
import com.sviridov.rememberka.step_service.StepInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "${eureka.feign.step-service-name}")
public interface FeignClientInstructionSteps {

    @PostMapping(value = "step-server/api/v1/steps")
    List<StepInfoDTO> getSteps(@RequestBody InstructionInfoDTO infoDTO);

    @PutMapping(value = "step-server/api/v1/steps")
    StepInfoDTO saveOneStep(@RequestBody StepInfoDTO stepInfoDTO, @RequestParam("userId") Long userId);

}
