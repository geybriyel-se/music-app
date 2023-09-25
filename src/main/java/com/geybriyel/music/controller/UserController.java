package com.geybriyel.music.controller;

import com.geybriyel.music.entity.User;
import com.geybriyel.music.response.ApiResponse;
import com.geybriyel.music.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


    // TODO: Add validation for username. Taken usernames should not proceed
    @PostMapping("/add")
    public ApiResponse addNewUser(@RequestBody User user) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "User successfully added");
        response.put("data", user);
        int i = userService.insertUser(user);
        log.info("Successfully added new user: {}", user);
        return new ApiResponse(HttpStatus.OK.value(), response);
    }

}
