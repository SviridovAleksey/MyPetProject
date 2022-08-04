package com.sviridov.resource.service.impl.users;

import com.sviridov.resource.error_handling.ResourceNotFoundException;
import com.sviridov.resource.persistence.model.users.Role;
import com.sviridov.resource.persistence.repository.users.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImp {

    private final RoleRepository roleRepository;

    public Role findRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Role by id " + id + " not found"));
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(()
                -> new ResourceNotFoundException("Role by name " + name + " not found"));
    }

}
