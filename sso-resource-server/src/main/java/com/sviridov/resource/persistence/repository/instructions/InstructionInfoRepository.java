package com.sviridov.resource.persistence.repository.instructions;

import com.sviridov.resource.persistence.model.instructions.InstructionsInfo;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.List;
import java.util.Optional;

public interface InstructionInfoRepository extends PagingAndSortingRepository<InstructionsInfo, Long> {

    Optional<InstructionsInfo> findByUserId(Long id);
    Optional<InstructionsInfo> findByUserName(Long id);
    Optional<InstructionsInfo> findByIdAndUserName(Long id, String userName);
    List<InstructionsInfo> findAllByUserId(Long id);
    List<InstructionsInfo> findAllByUserNameAndIsDelete(String name, boolean isDelete);
    List<InstructionsInfo> findAllByUserNameAndLevelOwnerAndIsDelete(String name,Long levelOwner, boolean isDelete);
}
