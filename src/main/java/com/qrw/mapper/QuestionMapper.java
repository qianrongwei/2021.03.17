package com.qrw.mapper;

/**
 * @author qrw
 * @create 2021-03-19 8:54
 */

import com.qrw.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    void insertQuestion(Question question);

    List<Question> list();
}
