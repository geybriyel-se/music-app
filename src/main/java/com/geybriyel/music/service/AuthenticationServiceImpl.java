package com.geybriyel.music.service;

import com.geybriyel.music.config.JwtService;
import com.geybriyel.music.controller.request.AuthenticationReq;
import com.geybriyel.music.controller.request.RegisterReq;
import com.geybriyel.music.controller.response.AuthenticationRes;
import com.geybriyel.music.entity.User;
import com.geybriyel.music.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserMapper repository;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationRes register(RegisterReq req) {
        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();
        repository.insertUser(user);
        String token = jwtService.generateToken(user);
        return AuthenticationRes.builder()
                .token(token).build();
    }

    @Override
    public AuthenticationRes authenticate(AuthenticationReq req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(), req.getPassword())
        );
        User user = repository.selectUserByUserName(req.getUsername());
        String token = jwtService.generateToken(user);
        return AuthenticationRes.builder()
                .token(token).build();

    }


}
