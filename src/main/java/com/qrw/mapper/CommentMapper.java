package com.qrw.mapper;

import com.qrw.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author qrw
 * @create 2021-03-20 18:10
 */
@Mapper
@Repository
public interface CommentMapper {

    void insert(Comment comment);

    Comment getComentById(Long id);
}
