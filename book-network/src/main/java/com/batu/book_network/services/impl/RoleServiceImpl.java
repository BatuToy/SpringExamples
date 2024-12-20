package com.batu.book_network.services.impl;

import com.batu.book_network.entites.Role;
import com.batu.book_network.config.exception.OperationNotPermittedException;
import com.batu.book_network.repositories.RoleRepository;
import com.batu.book_network.repositories.UserRepository;
import com.batu.book_network.entites.enums.Roles;
import com.batu.book_network.entites.User;
import com.batu.book_network.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final List<Role> userRoles = new ArrayList<>();

    public void addRole(User user, Roles roleType){
        Role role = Role
                    .builder()
                    .name(roleType.getRole())
                    .user(user)
                    .build();

        roleRepository.save(role);
        // TODO: Adding the roles in to a not permanent list
        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    public void deleteRole(Authentication connectedUser){
        User user = (User) connectedUser.getPrincipal();
        roleRepository.deleteAll(user.getRoles());
    }

    public boolean existByRole(User user){
        if(!roleRepository.existsRoleByUserId(user.getId())){
            throw new OperationNotPermittedException("Role has been not initialized!");
        }
        return true;
    }
}
