package com.lihao.service;

import com.lihao.domain.User;

import java.util.List;

public interface UserService {
    /**
     * 查询用户列表
     */
    List<User> findAll();

    /**
     * 根据id查询
     * @param userId
     * @return
     */
    User findById(Integer userId);

    /**
     * 更新用户
     * @param user
     */
    void updateUser(User user);
}
