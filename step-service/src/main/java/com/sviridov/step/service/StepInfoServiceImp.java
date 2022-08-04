package com.sviridov.step.service;


import com.sviridov.rememberka.resource_server.InstructionInfoDTO;
import com.sviridov.rememberka.step_service.StepInfoDTO;
import com.sviridov.step.persistence.model.StepInfo;
import com.sviridov.step.persistence.repository.StepInfoRepository;
import com.sviridov.step.web.dtos.MappingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StepInfoServiceImp {

    private final StepInfoRepository stepInfoRepository;
    private final StepTextServiceImp stepTextServiceImp;


    public List<StepInfoDTO> getStepsByInstInfo(InstructionInfoDTO infoDTO) {
        List<StepInfo> stepInfo = stepInfoRepository.findAllByInfoId(infoDTO.id());
        return stepInfo.stream()
                .map(MappingDTO::mapFromStepInfo)
                .collect(Collectors.toList());
    }

    public StepInfoDTO saveOneStep(StepInfoDTO stepInfoDTO, Long userId) {
        Optional<StepInfo> stepInfo = stepInfoRepository.findById(stepInfoDTO.id());
        if(stepInfo.isEmpty()) {
            stepInfo = Optional.of(MappingDTO.mapToStepInfo(stepInfoDTO));
        }
        stepInfo.get().setName(stepInfoDTO.name());
        if(stepInfoDTO.name().equals("")) {
            stepInfo.get().setName("New Step");
        }
       stepInfo.get().setStepText(stepTextServiceImp.saveStepText(MappingDTO.mapToStepText(stepInfoDTO.stepTextDTO())));
       stepInfo.get().setUserId(userId);
       return MappingDTO.mapFromStepInfo(stepInfoRepository.save(stepInfo.get()));
    }

}
