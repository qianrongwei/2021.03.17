package com.qrw.controller;

import com.qrw.cache.TagCache;
import com.qrw.dto.CommentDTO;
import com.qrw.dto.QuestionDTO;
import com.qrw.enums.CommentTypeEnum;
import com.qrw.pojo.User;
import com.qrw.service.CommentService;
import com.qrw.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qrw
 * @create 2021-03-20 9:55
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(value = "id") Long id, Model model, HttpServletRequest request){

        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //浏览数增加
        questionService.incView(id);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("commentDTOS",commentDTOS);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
