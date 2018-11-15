package com.test.yanglao.dao;

import com.test.yanglao.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    int checkPhone(String phone);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    List<User> selectAllUsers();
}