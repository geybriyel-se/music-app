package com.geybriyel.music.mapper;

import com.geybriyel.music.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<User> selectUserList();

    public User selectUserByUserName(String username);

    public User selectUserById(Long id);

    public User selectUserByEmail(String email);

    public int insertUser(User user);

}
