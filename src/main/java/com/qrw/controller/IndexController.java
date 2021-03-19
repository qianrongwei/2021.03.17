package com.qrw.controller;

import com.qrw.dto.PaginationDTO;
import com.qrw.dto.QuestionDTO;
import com.qrw.mapper.QuestionMapper;
import com.qrw.mapper.UserMapper;
import com.qrw.pojo.Question;
import com.qrw.pojo.User;
import com.qrw.service.QuestionService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.misc.Request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author qrw
 * @create 2021-03-17 18:40
 */

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = {"/"})
    public String hello(@RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "2") Integer size,
                        HttpServletRequest request, Model model){

        PaginationDTO paginationDTO = questionService.list(page, size);
        model.addAttribute("paginationDTO",paginationDTO);

        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie c : cookies) {
                if ("token".equals(c.getName())) {
                    String token = c.getValue();
                    User user = userMapper.findUserByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        return "index";
    }

}
