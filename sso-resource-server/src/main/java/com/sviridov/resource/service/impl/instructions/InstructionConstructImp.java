package com.sviridov.resource.service.impl.instructions;


import com.sviridov.rememberka.resource_server.ConstructDTO;
import com.sviridov.resource.persistence.model.instructions.InstructionConstruct;
import com.sviridov.resource.persistence.repository.instructions.InstructionConstructRepository;
import com.sviridov.resource.web.dto.MappingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructionConstructImp {

    private final InstructionConstructRepository instructionConstructRepository;


    public InstructionConstruct saveNewConstructFromDTO(ConstructDTO constructDTO) {
        return instructionConstructRepository.save(MappingDTO.mapConstructFromDTO(constructDTO));
    }

}
