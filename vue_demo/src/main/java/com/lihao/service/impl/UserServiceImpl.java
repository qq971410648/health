package com.lihao.service.impl;

import com.lihao.domain.User;
import com.lihao.mapper.UserMapper;
import com.lihao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findById(Integer userId) {
        return userMapper.findById(userId);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}
