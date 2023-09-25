package com.geybriyel.music.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Date registrationDate;

    private Date lastLogin;

    private String profilePictureUrl;
}
