package com.sviridov.resource.service.impl.users;

import com.sviridov.resource.persistence.model.users.Role;
import com.sviridov.resource.persistence.model.users.User;
import com.sviridov.resource.persistence.model.users.UserRole;
import com.sviridov.resource.persistence.repository.users.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserRoleServiceImp {

    private final UserRoleRepository userRoleRepository;

    public void saveNewUserRole(User user, Role role) {
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRoleRepository.save(userRole);
    }

}
