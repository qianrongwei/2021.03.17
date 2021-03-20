package com.qrw.controller;

import com.qrw.dto.PaginationDTO;
import com.qrw.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qrw
 * @create 2021-03-17 18:40
 */

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = {"/"})
    public String hello(@RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "5") Integer size,
                        Model model){

        PaginationDTO paginationDTO = questionService.list(page, size);
        model.addAttribute("paginationDTO",paginationDTO);
        return "index";
    }

}
