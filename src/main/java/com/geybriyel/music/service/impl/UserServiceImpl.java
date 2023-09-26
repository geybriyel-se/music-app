package com.geybriyel.music.service.impl;

import com.geybriyel.music.entity.User;
import com.geybriyel.music.service.UserService;
import com.geybriyel.music.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectUserList() {
        return userMapper.selectUserList();
    }

    @Override
    public User selectUserByUserName(String username) {
        return userMapper.selectUserByUserName(username);
    }

    @Override
    public User selectUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public int insertUser(User user) {
        User user1 = selectUserByUserName(user.getUsername());
        if (user1 == null) {
            userMapper.insertUser(user);
            return 0;
        } else {
            return -1;
        }
    }
}
