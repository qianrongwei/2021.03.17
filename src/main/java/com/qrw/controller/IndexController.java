package com.qrw.controller;

import com.qrw.mapper.UserMapper;
import com.qrw.pojo.User;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import sun.misc.Request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author qrw
 * @create 2021-03-17 18:40
 */

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = {"/","/index"})
    public String hello(HttpServletRequest request){
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
