package com.qrw.mapper;

import com.qrw.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author qrw
 * @create 2021-03-18 16:48
 */
@Repository
@Mapper
public interface UserMapper {

    @Insert(" insert into user(account_id,name,bio,token,gmt_create,gmt_modified,avatar_url)" +
            " values (#{accountId},#{name},#{bio},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl});")
    void insertUser(User user);

    @Select("select * from user where token = #{token};")
    User findUserByToken(@Value("token") String token);

    @Select("select * from user where id = #{id};")
    User findUserById(@Value("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Value("accountId") String accountId);

    @Update("update user set name = #{name},avatar_url = #{avatarUrl},token = #{token},gmt_modified = #{gmtModified}")
    void update(User user);
}