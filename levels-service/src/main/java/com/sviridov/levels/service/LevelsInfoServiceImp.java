package com.sviridov.levels.service;


import com.sviridov.levels.error_handling.ResourceNotFoundException;
import com.sviridov.levels.persistence.model.LevelInfo;
import com.sviridov.levels.persistence.repository.LevelInfoRepository;
import com.sviridov.levels.web.dtos.MappingDTO;
import com.sviridov.rememberka.levels_service.LevelsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LevelsInfoServiceImp {

    private final LevelInfoRepository levelInfoRepository;

    public List<LevelsDTO> getAllLevelInfoByUserId(Long userId) {
        return levelInfoRepository.findAllByUserId(userId).stream()
                .map(MappingDTO::mapFromLevelInfo).collect(Collectors.toList());
    }

    @Transactional
    public LevelsDTO saveOrUpdateLevel(LevelsDTO levelsDTO, Long userId) {
        Optional<LevelInfo> levelInfo = levelInfoRepository.getByIdAndUserId(levelsDTO.id(), userId);
        return levelInfo.map(info -> MappingDTO.mapFromLevelInfo(
                levelInfoRepository.save(MappingDTO.updateLevelInfo(info, levelsDTO))))
                .orElseGet(() -> MappingDTO.mapFromLevelInfo(levelInfoRepository
                        .save(MappingDTO.mapFromLevelDTO(levelsDTO, userId))));
    }

}
