package com.qrw.service.impl;

import com.qrw.dto.PaginationDTO;
import com.qrw.dto.QuestionDTO;
import com.qrw.exception.CustomizeErrorCode;
import com.qrw.exception.CustomizeException;
import com.qrw.mapper.QuestionExtMapper;
import com.qrw.mapper.QuestionMapper;
import com.qrw.mapper.UserMapper;
import com.qrw.pojo.Question;
import com.qrw.pojo.User;
import com.qrw.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;

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
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO list(Integer page,Integer size){

        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        Integer totalCount = questionMapper.getCount();
        Integer totalPage;
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }
        if(totalPage == 0){
            totalPage = 1;
        }
        if(page < 1 ){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question:questions) {
            User user = userMapper.findUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setData(questionDTOS);
        return paginationDTO;
    }

    public PaginationDTO listByUserId(Long userId,Integer page,Integer size){
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        Integer totalCount = questionMapper.getCountByUserID(userId);
        Integer totalPage;
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }
        if(page < 1 ){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question:questions) {
            User user = userMapper.findUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setData(questionDTOS);
        return paginationDTO;
    }

    @Override
    public QuestionDTO getById(Long id) {

        Question question = questionMapper.getById(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findUserById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null ){
            //插入
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0L);
            question.setCommentCount(0L);
            question.setLikeCount(0L);
            questionMapper.insertQuestion(question);
        }else{
            //更新
            question.setGmtModified(System.currentTimeMillis());
            int result = questionMapper.update(question);
            if(result != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id){
        /*
        一般会这么去做，先查询question原来的viewCount，在加一，有并发风险
         */
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1L);
        questionExtMapper.incView(question);
    }

    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO queryQuestionDTO) {
        if(StringUtils.isBlank(queryQuestionDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryQuestionDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryQuestionDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }

}
