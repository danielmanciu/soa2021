package com.soa.login.controller;

import com.soa.login.model.UserDto;
import com.soa.login.helpers.EndpointConstants;
import com.soa.login.repository.UserRepository;
import com.soa.login.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public LoginController(final UserService userService, final UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping(EndpointConstants.LOGIN)
    public void getUser() {
    }

    @PostMapping(EndpointConstants.REGISTER_USER)
    public void register(@RequestBody UserDto userDto) {
        if (userService.findByUserName(userDto.getUserName()) == null) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(userDto);
        }
    }

    @Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
