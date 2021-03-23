package com.qrw.mapper;

import com.qrw.dto.QuestionDTO;
import com.qrw.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qrw
 * @create 2021-03-20 17:13
 */
@Mapper
@Repository
public interface QuestionExtMapper {

     int incView(Question question);
     int incComment(Question question);

     //查询与question的标签 有关的question
     List<Question> selectRelated(Question question);
}
