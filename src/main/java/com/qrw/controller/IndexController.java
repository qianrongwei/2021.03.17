package com.qrw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qrw
 * @create 2021-03-17 18:40
 */

@Controller
public class IndexController {

    @GetMapping(value = {"/","index"})
    public String hello(){
        return "index";
    }

}
