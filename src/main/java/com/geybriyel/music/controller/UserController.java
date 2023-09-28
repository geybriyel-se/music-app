package com.geybriyel.music.controller;

import com.geybriyel.music.controller.request.AuthenticationReq;
import com.geybriyel.music.controller.request.RegisterReq;
import com.geybriyel.music.controller.request.UserLoginReq;
import com.geybriyel.music.controller.response.AuthenticationRes;
import com.geybriyel.music.entity.User;
import com.geybriyel.music.enums.ErrorCodes;
import com.geybriyel.music.response.ApiResponse;
import com.geybriyel.music.service.AuthenticationService;
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

    @Autowired
    private AuthenticationService service;

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

        if (i != 0) {
            return getValidationError(i);
        }

        log.info("Successfully added new user: {}", user);
        return new ApiResponse(HttpStatus.OK.value(), response);
    }

    @PostMapping("/auth/register")
    public ApiResponse authRegister(@RequestBody RegisterReq req) {
        AuthenticationRes response = service.register(req);
        return new ApiResponse(HttpStatus.OK.value(), response);
    }

    @PostMapping("/auth/authenticate")
    public ApiResponse authRegister(@RequestBody AuthenticationReq req) {
        AuthenticationRes response = service.authenticate(req);
        return new ApiResponse(HttpStatus.OK.value(), response);
    }


    @PostMapping("/login")
    public ApiResponse login(@RequestBody UserLoginReq user) {

        User loginUser = userService.selectUserByUserName(user.getUsername());

        if (loginUser == null) {
            return new ApiResponse(ErrorCodes.USER_DOES_NOT_EXIST.getCode(), ErrorCodes.USER_DOES_NOT_EXIST.getMessage());
        }

        boolean matches = passwordEncoder.matches(user.getPassword(), loginUser.getPassword());

        if (!matches) {
            log.error("Failed login attempt from user '{}'", user.getUsername());
            return new ApiResponse(ErrorCodes.INCORRECT_CREDENTIALS.getCode(), ErrorCodes.INCORRECT_CREDENTIALS.getMessage());
        }

        log.info("Login successful from user '{}'", user.getUsername());
        return new ApiResponse(HttpStatus.OK.value(), "Successfully logged in");
    }

    private ApiResponse getValidationError(int i) {
        if (i == -1) {
            log.error("Failed to add new user. Username not unique.");
            return new ApiResponse(ErrorCodes.USERNAME_NOT_UNIQUE.getCode(), ErrorCodes.USERNAME_NOT_UNIQUE.getMessage());
        }

        if (i == -2) {
            log.error("Failed to add new user. Email not unique.");
            return new ApiResponse(ErrorCodes.EMAIL_NOT_UNIQUE.getCode(), ErrorCodes.EMAIL_NOT_UNIQUE.getMessage());
        }

        if (i == -3) {
            log.error("Failed to add new user. Invalid email format.");
            return new ApiResponse(ErrorCodes.INVALID_EMAIL_FORMAT.getCode(), ErrorCodes.INVALID_EMAIL_FORMAT.getMessage());
        }
        return null;
    }


}
