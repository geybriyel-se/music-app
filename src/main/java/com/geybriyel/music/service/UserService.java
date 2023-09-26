package com.geybriyel.music.service;

import com.geybriyel.music.entity.User;

import java.util.List;

public interface UserService {

    public List<User> selectUserList();

    public User selectUserByUserName(String username);

    public User selectUserById(Long id);

    public User selectUserByEmail(String email);

    /**
     *
     * @param user
     * @return 0 successful, -1 failed:username taken, -2 failed:email taken, -3 failed:invalid email format
     */
    public int insertUser(User user);
}
