package com.qrw.service.impl;

import com.qrw.dto.CommentDTO;
import com.qrw.enums.CommentTypeEnum;
import com.qrw.enums.NotificationStatusEnum;
import com.qrw.enums.NotificationTypeEnum;
import com.qrw.exception.CustomizeErrorCode;
import com.qrw.exception.CustomizeException;
import com.qrw.mapper.*;
import com.qrw.pojo.Comment;
import com.qrw.pojo.Notification;
import com.qrw.pojo.Question;
import com.qrw.pojo.User;
import com.qrw.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    @Override
    public void insert(Comment comment,User commentator) {
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
            //创建通知
            createNotify(comment,question.getCreator(),commentator.getName(),question.getTitle(),NotificationTypeEnum.REPLY_QUESTION,question.getId());
        }else{
            //回复评论
            Comment dbcomment = commentMapper.getCommentById(comment.getParentId());
            System.out.println("64");
            if(dbcomment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Question question = questionMapper.getById(dbcomment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1L);
            commentMapper.incCommentCount(parentComment);
            //创建通知
            createNotify(comment,dbcomment.getCommentator(),commentator.getName(),question.getTitle(),NotificationTypeEnum.REPLY_COMMENT,question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationTypeEnum,Long outerId){
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(receiver);
        notification.setOuterId(outerId);
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setType(notificationTypeEnum.getType());
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    @Override
    public List<CommentDTO> listByTargetId(Long id,CommentTypeEnum type) {
        Comment commentExample = new Comment();
        commentExample.setParentId(id);
        commentExample.setType(type.getType());
        List<Comment> comments =commentMapper.getCommentsByExample(commentExample);
        if (comments.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        //获取评论人并转换成Map
        List<User> users = userMapper.getUsersByIds(userIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        //转换comment为CommentDTO
        List<CommentDTO> commentDTOS =comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
