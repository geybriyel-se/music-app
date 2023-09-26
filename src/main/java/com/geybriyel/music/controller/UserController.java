package com.geybriyel.music.controller;

import com.geybriyel.music.controller.request.UserLoginReq;
import com.geybriyel.music.entity.User;
import com.geybriyel.music.enums.ErrorCodes;
import com.geybriyel.music.response.ApiResponse;
import com.geybriyel.music.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public ApiResponse getAllUsers() {
        List<User> users = userService.selectUserList();
        log.info("Successfully retrieved all users: {}", users);
        return new ApiResponse(HttpStatus.OK.value(), users);
    }

    @GetMapping("/id/{id}")
    public ApiResponse getUserById(@PathVariable Long id) {
        User user = userService.selectUserById(id);
        log.info("Successfully retrieved user with id {}: {}", id, user);
        return new ApiResponse(HttpStatus.OK.value(), user);
    }

    @GetMapping("/username/{username}")
    public ApiResponse getUserByUsername(@PathVariable String username) {
        User user = userService.selectUserByUserName(username);
        log.info("Successfully retrieved user with username '{}': {}", username, user);
        return new ApiResponse(HttpStatus.OK.value(), user);
    }

    @PostMapping("/register")
    public ApiResponse register(@RequestBody User user) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "User successfully added");
        response.put("data", user);
        int i = userService.insertUser(user);
        if (i == -1) {
            log.error("Failed to add new user. Username not unique.");
            return new ApiResponse(ErrorCodes.USERNAME_NOT_UNIQUE.getCode(), ErrorCodes.USERNAME_NOT_UNIQUE.getMessage());
        }
        log.info("Successfully added new user: {}", user);
        return new ApiResponse(HttpStatus.OK.value(), response);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody UserLoginReq user) {
        User loginUser = userService.selectUserByUserName(user.getUsername());
        boolean matches = passwordEncoder.matches(user.getPassword(), loginUser.getPassword());

        if (!matches) {
            log.error("Failed login attempt from user '{}'", user.getUsername());
            return new ApiResponse(ErrorCodes.INCORRECT_CREDENTIALS.getCode(), ErrorCodes.INCORRECT_CREDENTIALS.getMessage());
        }

        log.info("Login successful from user '{}'", user.getUsername());
        return new ApiResponse(HttpStatus.OK.value(), "Successfully logged in");
    }


}
