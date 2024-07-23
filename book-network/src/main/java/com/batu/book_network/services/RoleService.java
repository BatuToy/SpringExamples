package com.batu.book_network.services;

import com.batu.book_network.entites.User;
import com.batu.book_network.enums.Roles;
import org.springframework.security.core.Authentication;

public interface RoleService {
    void addRole(User user, Roles roleType);
    void deleteRole(Authentication connectedUser);
    boolean existByRole(User user);
}
