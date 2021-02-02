package com.soa.login.controller;

import com.soa.login.model.UserDto;
import com.soa.login.repository.UserRepository;
import com.soa.login.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userRepository.findAll();

        if (!users.isEmpty()) {
            log.info("All users: " + users.size());
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            log.error("No users found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
        UserDto userDto = userRepository.getUserById(id);
        log.info("user" + userDto);
        if (userDto != null) {
            log.info("All users: " + userDto);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            log.error("No users found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> getUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto loggedUser = userRepository.findByUserName(userName);
        if (loggedUser != null) {
            return new ResponseEntity<>(loggedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}