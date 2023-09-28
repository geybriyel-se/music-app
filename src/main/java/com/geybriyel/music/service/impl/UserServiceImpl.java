package com.geybriyel.music.service.impl;

import com.geybriyel.music.entity.User;
import com.geybriyel.music.service.UserService;
import com.geybriyel.music.mapper.UserMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

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
    public User selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    @Override
    public int insertUser(User user) {
        User userByUserName = selectUserByUserName(user.getUsername());
        User userByEmail = selectUserByEmail(user.getEmail());

        if (userByUserName != null) {
            return -1;
        }
        if (userByEmail != null) {
            return -2;
        }

        if (!isValidEmail(user.getEmail())) {
            return -3;
        }

        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashpw);
        userMapper.insertUser(user);
        return 0;
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")
                .matcher(email)
                .matches();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return selectUserByUserName(username);
    }
}
