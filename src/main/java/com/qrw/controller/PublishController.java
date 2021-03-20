package com.qrw.controller;

import com.qrw.dto.QuestionDTO;
import com.qrw.mapper.QuestionMapper;
import com.qrw.mapper.UserMapper;
import com.qrw.pojo.Question;
import com.qrw.pojo.User;
import com.qrw.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qrw
 * @create 2021-03-18 21:14
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String toPublishPage(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title, @RequestParam("description") String description,
                            @RequestParam("tag") String tag, @RequestParam(value = "id",required = false) Integer id,
                            Model model, HttpServletRequest request){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","请登录后在发布问题");
            return "publish";
        }

        if(title == null || "".equals(title.trim())){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description == null || "".equals(description.trim())){
            model.addAttribute("error","问题内容不能为空");
            return "publish";
        }
        if(tag == null || "".equals(tag.trim())){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setId(id);
        question.setCreator(user.getId());
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

    @GetMapping(value = "/publish/{id}")
    public String edit(@PathVariable("id") Integer id,Model model){

        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",id);
        return "publish";
    }

}
