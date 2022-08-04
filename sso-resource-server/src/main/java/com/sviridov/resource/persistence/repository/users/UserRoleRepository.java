package com.sviridov.resource.persistence.repository.users;

import com.sviridov.resource.persistence.model.users.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
