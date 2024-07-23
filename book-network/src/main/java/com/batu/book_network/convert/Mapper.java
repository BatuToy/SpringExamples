package com.batu.book_network.convert;

import com.batu.book_network.request.RegistrationRequest;
import com.batu.book_network.response.AuthorizeManagerResponse;
import com.batu.book_network.response.ChangePasswordResponse;
import com.batu.book_network.response.RegistrationResponse;
import com.batu.book_network.entites.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public Mapper(ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.mapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        configModelMapper();
    }

    public void configModelMapper(){
        mapper.typeMap(User.class, RegistrationResponse.class).addMappings(mapper -> {
            mapper.map(User::getEmail, RegistrationResponse::setEmail);
            mapper.map(User::getFullName, RegistrationResponse::setFullName);
        });

        mapper.typeMap(User.class, RegistrationResponse.class).addMappings( mapper -> {
            mapper.map(User::getRoles, RegistrationResponse::setRoles);
        });

        mapper.typeMap(User.class, AuthorizeManagerResponse.class).addMappings( mapper ->{
            mapper.map(User::getFullName, AuthorizeManagerResponse::setFullName);
            mapper.map(User::getRoles, AuthorizeManagerResponse::setRoles);
        });

    }

    public User registerToUser(RegistrationRequest request){
        User user = mapper.map(request, User.class);
        user.setPassWord(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    public RegistrationResponse userToResponse(User user){
        return mapper.map(user, RegistrationResponse.class);
    }


    public AuthorizeManagerResponse toAuthorizeManagerResponse(User user) {
        return mapper.map(user, AuthorizeManagerResponse.class);
    }

    public ChangePasswordResponse toChangePasswordResponse(User user){
        return mapper.map(user, ChangePasswordResponse.class);
    }
}
