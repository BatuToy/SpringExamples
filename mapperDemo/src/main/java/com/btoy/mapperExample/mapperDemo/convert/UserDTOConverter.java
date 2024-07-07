package com.btoy.mapperExample.mapperDemo.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.btoy.mapperExample.mapperDemo.dto.UserDTO;
import com.btoy.mapperExample.mapperDemo.model.User;

@Component
public class UserDTOConverter {
    
    @Autowired
    private ModelMapper modelmapper;

    public UserDTO convertUserToUserDTO(User user) {
        
        UserDTO userDTO = modelmapper.map(user, UserDTO.class);
        
        userDTO.setFullName(user.getFirstName() + " " + user.getLastName());

        return userDTO;
    }

    public User convertUserDTOToUser(UserDTO userDTO) {
        
        User user = modelmapper.map(userDTO, User.class);

        return user;
    }
}
