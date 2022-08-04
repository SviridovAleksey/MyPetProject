package com.sviridov.step.persistence.repository;

import com.sviridov.step.persistence.model.StepInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepInfoRepository extends JpaRepository<StepInfo, Long> {

    List<StepInfo> findAllByInfoId(Long id);

}
