package com.batu.book_network.services;

import com.batu.book_network.entites.Role;
import com.batu.book_network.exception.OperationNotPermittedException;
import com.batu.book_network.repositories.RoleRepository;
import com.batu.book_network.repositories.UserRepository;
import com.batu.book_network.enums.Roles;
import com.batu.book_network.entites.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

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
