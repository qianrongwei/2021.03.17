package com.qrw.service;

import com.qrw.dto.PaginationDTO;
import com.qrw.dto.QuestionDTO;
import com.qrw.pojo.Question;

/**
 * @author qrw
 * @create 2021-03-19 10:33
 */
public interface QuestionService {

     PaginationDTO list(Integer page, Integer size);

     PaginationDTO listByUserId(Integer userId,Integer page,Integer size);

     QuestionDTO getById(Integer id);

     void createOrUpdate(Question question);

     void incView(Integer id);
}
