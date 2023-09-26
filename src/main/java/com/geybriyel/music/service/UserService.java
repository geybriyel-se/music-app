package com.geybriyel.music.service;

import com.geybriyel.music.entity.User;

import java.util.List;

public interface UserService {

    public List<User> selectUserList();

    public User selectUserByUserName(String username);

    public User selectUserById(Long id);

    /**
     *
     * @param user
     * @return 0 successful, -1 failed:user already exists
     */
    public int insertUser(User user);
}
