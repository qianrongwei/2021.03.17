package com.qrw.service.impl;

import com.qrw.enums.CommentTypeEnum;
import com.qrw.exception.CustomizeErrorCode;
import com.qrw.exception.CustomizeException;
import com.qrw.mapper.CommentMapper;
import com.qrw.mapper.QuestionExtMapper;
import com.qrw.mapper.QuestionMapper;
import com.qrw.pojo.Comment;
import com.qrw.pojo.Question;
import com.qrw.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qrw
 * @create 2021-03-20 21:49
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private  QuestionExtMapper questionExtMapper;

    @Override
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || ! CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.QUESTION.getType()){
            //回复问题
            Question question = questionMapper.getById(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1L);
            questionExtMapper.incComment(question);
        }else{
            //回复评论
            Comment dbcomment = commentMapper.getComentById(comment.getParentId());
            if(dbcomment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }
    }
}
