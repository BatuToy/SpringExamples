package com.batu.book_network.convert;

import com.batu.book_network.auth.RegistrationRequest;
import com.batu.book_network.auth.RegistrationResponse;
import com.batu.book_network.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public Mapper(ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        configModelMapper();
    }

    public void configModelMapper(){
        modelMapper.typeMap(User.class, RegistrationResponse.class).addMappings(mapper -> {
            mapper.map(User::getEmail, RegistrationResponse::setEmail);
            mapper.map(User::getFullName, RegistrationResponse::setFullName);
        });
    }

    public User registerToUser(RegistrationRequest request){
        User user = modelMapper.map(request, User.class);
        user.setPassWord(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    public RegistrationResponse userToResponse(User user){
        return modelMapper.map(user, RegistrationResponse.class);
    }


}
