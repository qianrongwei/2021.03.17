package com.qrw.mapper;

import com.qrw.pojo.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qrw
 * @create 2021-03-22 15:40
 */
@Mapper
@Repository
public interface NotificationMapper {

    void insert(Notification notification);

    Integer getCountByUserId(Long userId);

    @Select("select count(1) from notification where receiver = #{id} and status = #{status};")
    Long unreadCount(@Param("id") Long userId, @Param("status") Integer status);

    @Select("select * from notification where receiver = #{userId} order by gmt_create desc limit #{offset},#{size}")
    List<Notification> listByUserId(@Param("userId") Long userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from notification where id = #{id}")
    Notification selectById(@Param("id") Long id);

    @Update("update notification set status = #{status} where id = #{id}")
    Integer updateById(Notification notification);
}
