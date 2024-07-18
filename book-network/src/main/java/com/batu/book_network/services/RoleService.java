package com.batu.book_network.services;

import com.batu.book_network.entites.Role;
import com.batu.book_network.repositories.RoleRepository;
import com.batu.book_network.role.Roles;
import com.batu.book_network.entites.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public Role existByRoleName(String name) {
        return roleRepository.findByName(name)
               .orElseThrow(() -> new IllegalStateException("Role user was not Initialized!"));
    }

    public Role addRole(User user, Roles roleType){
        Role role = Role
                    .builder()
                    .name(roleType.name())
                    .user(user)
                    .build();

        roleRepository.save(role);
        return role;
    }

    public void deleteRole(Role role){
        roleRepository.delete(role);
    }
}
