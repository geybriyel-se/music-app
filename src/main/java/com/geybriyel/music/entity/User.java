package com.geybriyel.music.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class User implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Date registrationDate;

    private Date lastLogin;

    private String profilePicUrl;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    private Set<GrantedAuthority> authorities;

}
