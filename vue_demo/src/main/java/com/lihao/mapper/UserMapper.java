package com.lihao.mapper;

import com.lihao.domain.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    /**
     * 查询用户列表
     */
    @Select("select * from user")
    List<User> findAll();

    /**
     * 根据id查询
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{userId} ")
    User findById(Integer userId);

    /**
     * 更新用户
     * @param user
     */
    @Update("update user set username=#{username},password=#{password},age=#{age},sex=#{sex},email=#{email} where id=#{id}")
    void updateUser(User user);

}
