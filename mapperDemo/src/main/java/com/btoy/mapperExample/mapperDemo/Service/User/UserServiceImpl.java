package com.btoy.mapperExample.mapperDemo.Service.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.btoy.mapperExample.mapperDemo.convert.UserDTOConverter;
import com.btoy.mapperExample.mapperDemo.dto.UserDTO;
import com.btoy.mapperExample.mapperDemo.model.User;
import com.btoy.mapperExample.mapperDemo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    
    private final List<UserDTO> userDTOs = new ArrayList<>();
    private final UserRepository userRepository;
    private final UserDTOConverter userDTOConverter;
    
    public UserServiceImpl(UserRepository userRepository, UserDTOConverter userDTOConverter) {
        this.userRepository = userRepository;
        this.userDTOConverter = userDTOConverter;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
             var userDTO = userDTOConverter.convertUserToUserDTO(user);
             userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public UserDTO addUser(User user) {
        userRepository.save(user);
        var userDTO = userDTOConverter.convertUserToUserDTO(user);
        return userDTO;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        var user = userRepository.getUserById(userId);
        var userDTO = userDTOConverter.convertUserToUserDTO(user);
        return userDTO;
    }

    @Override
    public void deleteAllUsers() {  
        userRepository.deleteAll();
    }

    @Override
    public void deleteUserById(Long userId) {  
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(noRollbackFor = RuntimeException.class)
    public UserDTO updateUser(Long userId, User user) {
        Optional<User> userOptional = Optional.of(userRepository.getUserById(userId));
        User userToUpdate = userOptional.get();
        if(userOptional.isPresent()){
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPassword(user.getPassword());
            userRepository.save(userToUpdate);
            var userDTO = userDTOConverter.convertUserToUserDTO(userToUpdate);
            return userDTO;
        } else {
            throw new RuntimeException("User can't be null!");
        }
    }

}
