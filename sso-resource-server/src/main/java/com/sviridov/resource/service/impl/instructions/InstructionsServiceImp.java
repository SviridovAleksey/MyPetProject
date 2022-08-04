package com.sviridov.resource.service.impl.instructions;

import com.sviridov.rememberka.resource_server.InstructionInfoDTO;
import com.sviridov.resource.error_handling.ResourceNotFoundException;
import com.sviridov.resource.persistence.model.instructions.InstructionsInfo;
import com.sviridov.resource.persistence.repository.instructions.InstructionInfoRepository;
import com.sviridov.resource.service.impl.users.UserServiceImp;
import com.sviridov.resource.web.dto.MappingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructionsServiceImp {

    private final InstructionInfoRepository instructionInfoRepository;
    private final UserServiceImp userServiceImp;
    private final InstructionConstructImp instructionConstructImp;


    public InstructionInfoDTO getInstructionDTO(Long idInstruction, String userName) {
        return MappingDTO.mapInstructionDTO(getInstruction(idInstruction, userName));
    }

    public List<InstructionInfoDTO> getAllInstructionByUserName(String userName) {
        List<InstructionsInfo> instructionsInfo = instructionInfoRepository.
                findAllByUserNameAndIsDelete(userName, false);
        return instructionsInfo.stream()
                .map(MappingDTO::mapInstructionDTO)
                .collect(Collectors.toList());
    }

    public List<InstructionInfoDTO> getAllInstructionByUserNameAndIdFolder(String userName, Long id) {
        List<InstructionsInfo> instructionsInfo = instructionInfoRepository.
                findAllByUserNameAndLevelOwnerAndIsDelete(userName, id, false);
        return instructionsInfo.stream()
                .map(MappingDTO::mapInstructionDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public InstructionInfoDTO saveNewInstructionInfo(String userName, InstructionInfoDTO infoDTO) {
        Optional<InstructionsInfo> info = instructionInfoRepository.findByIdAndUserName(infoDTO.id(), userName);
        if(info.isEmpty()) {
            info = Optional.of(new InstructionsInfo());
        }
        if (infoDTO.name().equals("")) {
            info.get().setName("New instruction");
        } else {
            info.get().setName(infoDTO.name());
        }
        info.get().setUser(userServiceImp.findUserByName(userName));
        info.get().setDescription(infoDTO.description());
        info.get().setLevelOwner(infoDTO.levelOwner());
        info.get().setInstructionConstruct(instructionConstructImp.saveNewConstructFromDTO(infoDTO.constructDTO()));
        return MappingDTO.mapInstructionDTO(instructionInfoRepository.save(info.get()));
    }

    @Transactional
    public void deleteInstruction(Long idInstruction, String userName) {
       getInstruction(idInstruction, userName).setDelete(true);
    }

    private InstructionsInfo getInstruction(Long idInstruction, String userName) {
        InstructionsInfo info = instructionInfoRepository.findByIdAndUserName(idInstruction, userName)
                .orElseThrow(()
                        -> new ResourceNotFoundException("The instruction not found"));
        if(info.isDelete()) {
            throw new ResourceNotFoundException("The instruction has already been deleted");
        }
        return info;
    }



}
