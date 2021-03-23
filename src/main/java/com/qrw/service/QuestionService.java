package com.qrw.service;

import com.qrw.dto.PaginationDTO;
import com.qrw.dto.QuestionDTO;
import com.qrw.pojo.Question;

import java.util.List;

/**
 * @author qrw
 * @create 2021-03-19 10:33
 */
public interface QuestionService {

     PaginationDTO list(Integer page, Integer size);

     PaginationDTO listByUserId(Long userId,Integer page,Integer size);

     QuestionDTO getById(Long id);

     void createOrUpdate(Question question);

     void incView(Long id);

    List<QuestionDTO> selectRelated(QuestionDTO questionDTO);
}
