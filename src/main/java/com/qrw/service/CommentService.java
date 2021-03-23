package com.qrw.service;

import com.qrw.dto.CommentDTO;
import com.qrw.enums.CommentTypeEnum;
import com.qrw.pojo.Comment;
import com.qrw.pojo.User;

import java.util.List;

/**
 * @author qrw
 * @create 2021-03-20 21:48
 */
public interface CommentService {

    void insert(Comment comment, User user);

    List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type);
}
