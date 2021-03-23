package com.qrw.mapper;

import com.qrw.pojo.Comment;
import com.qrw.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qrw
 * @create 2021-03-20 18:10
 */
@Mapper
@Repository
public interface CommentMapper {

    void insert(Comment comment);

    Comment getCommentById(Long id);

    List<Comment> getCommentsByExample(Comment comment);

    int incCommentCount(Comment comment);
}
