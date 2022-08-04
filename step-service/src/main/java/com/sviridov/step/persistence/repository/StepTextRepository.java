package com.sviridov.step.persistence.repository;

import com.sviridov.step.persistence.model.StepText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepTextRepository extends JpaRepository<StepText, Long> {
}
