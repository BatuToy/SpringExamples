package com.btoy.mapperExample.mapperDemo.Service;

import java.util.List;

import com.btoy.mapperExample.mapperDemo.dto.UserDTO;
import com.btoy.mapperExample.mapperDemo.model.User;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO addUser(User user);
    UserDTO getUserById(Long userId);
    void deleteAllUsers();
    void deleteUserById(Long userId);
}
