package com.qrw.mapper;

import com.qrw.pojo.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qrw
 * @create 2021-03-19 8:54
 */
@Repository
@Mapper
public interface QuestionMapper {

    @Insert(" insert into question(title,description,tag,creator,gmt_create,gmt_modified)" +
            " values(#{title},#{description},#{tag},#{creator},#{gmtCreate},#{gmtModified});")
    void insertQuestion(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer getCount();

    @Select("select * from question where creator = #{creator} limit #{offset},#{size}")
    List<Question> listByUserId(@Param("creator") Long userId,@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer getCountByUserID(@Param("userId") Long userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Long id);

    @Update("update question set title = #{title},description = #{description},tag = #{tag} where id = #{id}")
    int update(Question question);

}
