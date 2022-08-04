package com.sviridov.resource.service.impl.users;


import com.sviridov.resource.error_handling.ResourceNotFoundException;
import com.sviridov.resource.persistence.model.users.User;
import com.sviridov.resource.persistence.repository.users.UsersRepository;
import com.sviridov.resource.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp {

    private final UsersRepository usersRepository;
    private final RoleServiceImp roleServiceImp;
    private final UserRoleServiceImp userRoleServiceImp;

    public UserDTO findUserDTOByName(String name) {
        User user = usersRepository.findByName(name).orElseThrow(()
                -> new ResourceNotFoundException("User with username " + name + " not found"));
        return new UserDTO(user.getId(), user.getName());
    }

    public User findUserByName(String name) {
        User user = usersRepository.findByName(name).orElseThrow(()
                -> new ResourceNotFoundException("User with username " + name + " not found"));
        return user;
    }

    public boolean checkExistUserAndSaveNew(String name) {
        if (usersRepository.findByName(name).isPresent()) {
            return true;
        } else {
            saveNewUser(name);
            return false;
        }
    }

    public void saveNewUser(String name) {
        User user = new User();
        user.setName(name);
        usersRepository.save(user);
        saveRoleNewUser(user);
    }

    private void saveRoleNewUser(User user){
        userRoleServiceImp.saveNewUserRole(user, roleServiceImp.findRoleByName("USER"));
    }



}
