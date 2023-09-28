package com.geybriyel.music.service;

import com.geybriyel.music.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> selectUserList();

    User selectUserByUserName(String username);

    User selectUserById(Long id);

    User selectUserByEmail(String email);

    /**
     *
     * @param user
     * @return 0 successful, -1 failed:username taken, -2 failed:email taken, -3 failed:invalid email format
     */
    public int insertUser(User user);
}
