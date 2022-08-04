package com.sviridov.levels.web.dtos;


import com.sviridov.levels.persistence.model.LevelInfo;
import com.sviridov.rememberka.levels_service.LevelsDTO;


public class MappingDTO {

    public static LevelsDTO mapFromLevelInfo(LevelInfo levelInfo) {
        return new LevelsDTO(levelInfo.getId(), levelInfo.getName(), levelInfo.getOwner(), levelInfo.getLevelPlace());
    }

    public static LevelInfo mapFromLevelDTO(LevelsDTO levelsDTO, Long userId) {
        LevelInfo levelInfo = new LevelInfo();
        levelInfo.setName(levelsDTO.name());
        levelInfo.setOwner(levelsDTO.owner());
        levelInfo.setLevelPlace(levelsDTO.levelPlace());
        levelInfo.setUserId(userId);
        levelInfo.setIsDeleted(false);
        return levelInfo;
    }

    public static LevelInfo updateLevelInfo(LevelInfo levelInfo, LevelsDTO levelsDTO){
        levelInfo.setName(levelsDTO.name());
        levelInfo.setLevelPlace(levelsDTO.levelPlace());
        levelInfo.setOwner(levelsDTO.owner());
        return levelInfo;
    }

}
