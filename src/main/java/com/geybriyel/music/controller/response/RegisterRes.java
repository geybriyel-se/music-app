package com.geybriyel.music.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRes {

    private Long id;

    private String username;

    private String email;
}
