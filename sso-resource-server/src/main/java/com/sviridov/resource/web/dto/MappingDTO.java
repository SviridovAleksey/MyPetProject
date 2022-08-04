package com.sviridov.resource.web.dto;

import com.sviridov.rememberka.resource_server.ConstructDTO;
import com.sviridov.rememberka.resource_server.InstructionInfoDTO;
import com.sviridov.resource.persistence.model.instructions.InstructionConstruct;
import com.sviridov.resource.persistence.model.instructions.InstructionsInfo;

public class MappingDTO {

    public static InstructionInfoDTO mapInstructionDTO(InstructionsInfo inst) {
       return new InstructionInfoDTO(inst.getId(), inst.getName(),
                inst.getDescription(),
                inst.getLevelOwner(),
                mapInfoConstruct(inst.getInstructionConstruct())
        );
    }

    public static ConstructDTO mapInfoConstruct(InstructionConstruct con) {
       return new ConstructDTO(con.getName(), con.getValue());
    }

    public static InstructionConstruct mapConstructFromDTO(ConstructDTO constructDTO) {
        InstructionConstruct instructionConstruct = new InstructionConstruct();
        instructionConstruct.setName(constructDTO.name());
        instructionConstruct.setValue(constructDTO.value());
        return instructionConstruct;
    }

}
