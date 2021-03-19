package com.qrw.controller;

import com.qrw.dto.PaginationDTO;
import com.qrw.mapper.UserMapper;
import com.qrw.pojo.User;
import com.qrw.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author qrw
 * @create 2021-03-19 22:16
 */
@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name="action") String action,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "5") Integer size,
                          Model model, HttpServletRequest request){

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else{
            if("replies".equals(action)){
                model.addAttribute("section","replies");
                model.addAttribute("sectionName","最新答复");
            }
        }

        Cookie[] cookies = request.getCookies();
        User user = null;
        if(cookies != null) {
            for (Cookie c : cookies) {
                if ("token".equals(c.getName())) {
                    String token = c.getValue();
                    user = userMapper.findUserByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if(user == null){
            return "redirect:/";
        }
        PaginationDTO paginationDTO = questionService.listByUserId(user.getId(),page, size);
        model.addAttribute("paginationDTO",paginationDTO);

        return "profile";
    }
}
