package com.qrw.controller;

import com.qrw.dto.PaginationDTO;
import com.qrw.pojo.User;
import com.qrw.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qrw
 * @create 2021-03-19 22:16
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name="action") String action,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "5") Integer size,
                          Model model, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.listByUserId(user.getId(),page, size);
            model.addAttribute("paginationDTO",paginationDTO);
        }else{
            if("replies".equals(action)){
                model.addAttribute("section","replies");
                model.addAttribute("sectionName","最新答复");
            }
        }
        return "profile";
    }
}
