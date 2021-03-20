package com.qrw.controller;

import com.qrw.mapper.QuestionMapper;
import com.qrw.mapper.UserMapper;
import com.qrw.pojo.Question;
import com.qrw.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String toPublishPage(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title, @RequestParam("description") String description,
                            @RequestParam("tag") String tag, Model model, HttpServletRequest request){

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
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.insertQuestion(question);
        return "redirect:/";
    }

}
