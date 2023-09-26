package com.geybriyel.music.controller.request;

import lombok.Data;

@Data
public class UserLoginReq {

    private String username;

    private String email;

    private String password;
}
