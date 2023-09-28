package com.geybriyel.music.controller;

import com.geybriyel.music.controller.request.LoginReq;
import com.geybriyel.music.controller.response.RegisterRes;
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
        if (users.isEmpty()) {
            log.info("No users");
            return new ApiResponse(HttpStatus.OK.value(), "There are no users");
        }
        log.info("Successfully retrieved all users: {}", users);
        return new ApiResponse(HttpStatus.OK.value(), users);
    }

    @GetMapping("/id/{id}")
    public ApiResponse getUserById(@PathVariable Long id) {
        User user = userService.selectUserById(id);
        if (user == null) {
            log.error("No records with id '{}", id);
            return new ApiResponse(ErrorCodes.INVALID_USER_ID.getCode(), ErrorCodes.INVALID_USER_ID.getMessage());
        }
        log.info("Successfully retrieved user with id {}: {}", id, user);
        return new ApiResponse(HttpStatus.OK.value(), user);
    }

    @GetMapping("/username/{username}")
    public ApiResponse getUserByUsername(@PathVariable String username) {
        User user = userService.selectUserByUserName(username);
        if (user == null) {
            log.error("No records with username '{}", username);
            return new ApiResponse(ErrorCodes.USER_DOES_NOT_EXIST.getCode(), ErrorCodes.USER_DOES_NOT_EXIST.getMessage());
        }
        log.info("Successfully retrieved user with username '{}': {}", username, user);
        return new ApiResponse(HttpStatus.OK.value(), user);
    }

    @PostMapping("/register")
    public ApiResponse register(@RequestBody User user) {
        User userByUserName = userService.selectUserByUserName(user.getUsername());
        if (userByUserName != null) {
            log.error("Username is already taken");
            return new ApiResponse(ErrorCodes.USERNAME_NOT_UNIQUE.getCode(), ErrorCodes.USERNAME_NOT_UNIQUE.getMessage());
        }


        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "User successfully added");
        response.put("data", user);
        int i = userService.insertUser(user);

        if (i != 0) {
            return getValidationError(i);
        }

        RegisterRes res = RegisterRes.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        log.info("Successfully added new user: {}", res);
        return new ApiResponse(HttpStatus.OK.value(), res);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginReq user) {
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
