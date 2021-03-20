package com.qrw.mapper;

import com.qrw.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author qrw
 * @create 2021-03-20 17:13
 */
@Mapper
@Repository
public interface QuestionExtMapper {

     void incView(Question question);
}
