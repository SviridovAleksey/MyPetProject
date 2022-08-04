package com.sviridov.levels.persistence.repository;


import com.sviridov.levels.persistence.model.LevelInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LevelInfoRepository extends JpaRepository<LevelInfo, Long> {

    List<LevelInfo> findAllByUserId(Long userId);
    Optional<LevelInfo> getByIdAndUserId(Long id, Long userId);
}
