package com.sviridov.resource.persistence.repository.instructions;

import com.sviridov.resource.persistence.model.instructions.InstructionConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InstructionConstructRepository extends JpaRepository<InstructionConstruct, Long> {
}
