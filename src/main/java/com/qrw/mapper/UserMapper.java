package com.qrw.mapper;

import com.qrw.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author qrw
 * @create 2021-03-18 16:48
 */
@Repository
@Mapper
public interface UserMapper {

    void insertUser(User user);

    User findUserByToken(String token);

    User findUserById(Integer id);
}
