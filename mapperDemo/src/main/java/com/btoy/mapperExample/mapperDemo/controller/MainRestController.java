package com.btoy.mapperExample.mapperDemo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btoy.mapperExample.mapperDemo.Service.UserService;
import com.btoy.mapperExample.mapperDemo.dto.UserDTO;
import com.btoy.mapperExample.mapperDemo.model.User;

@RestController
@RequestMapping("/api/v1")
public class MainRestController {
    
    private final UserService userService;

    public MainRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> addUser(@RequestBody User user){
        var userDTO =userService.addUser(user);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getUser() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        var userDTO = userService.getUserById(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteAllUsers(){
        userService.deleteAllUsers();
        return new ResponseEntity<>("User deleted!", HttpStatus.CONFLICT);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return new ResponseEntity<>("Id with" + " " + userId +" is deleted!", HttpStatus.CONFLICT);
    }
}
