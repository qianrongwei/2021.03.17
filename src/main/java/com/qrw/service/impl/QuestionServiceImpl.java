package com.qrw.service.impl;

import com.qrw.dto.QuestionDTO;
import com.qrw.mapper.QuestionMapper;
import com.qrw.mapper.UserMapper;
import com.qrw.pojo.Question;
import com.qrw.pojo.User;
import com.qrw.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qrw
 * @create 2021-03-19 10:34
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list(){
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question:questions) {
            User user = userMapper.findUserById(question.getCreator());
            System.out.println(user);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}
