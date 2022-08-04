package com.sviridov.step.web.dtos;

import com.sviridov.rememberka.step_service.StepInfoDTO;
import com.sviridov.rememberka.step_service.StepTextDTO;
import com.sviridov.step.persistence.model.StepInfo;
import com.sviridov.step.persistence.model.StepText;

public class MappingDTO {

    public static StepInfoDTO mapFromStepInfo(StepInfo stepInfo) {
       return new StepInfoDTO(stepInfo.getId(), stepInfo.getName(), stepInfo.getStepPlace(),
               stepInfo.getInfoId(), mapFromStepText(stepInfo.getStepText()));
    }

    public static StepTextDTO mapFromStepText(StepText stepText) {
        return new StepTextDTO(stepText.getText());
    }

    public static StepText mapToStepText(StepTextDTO stepTextDTO) {
        StepText stepText = new StepText();
        stepText.setText(stepTextDTO.text());
        return stepText;
    }

    public static StepInfo mapToStepInfo(StepInfoDTO stepInfoDTO) {
        StepInfo stepInfo = new StepInfo();
        stepInfo.setName(stepInfoDTO.name());
        stepInfo.setStepPlace(stepInfoDTO.stepPlace());
        stepInfo.setInfoId(stepInfoDTO.infoId());
        stepInfo.setStepText(MappingDTO.mapToStepText(stepInfoDTO.stepTextDTO()));
        return stepInfo;
    }
}
