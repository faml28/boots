package com.fml.controller;

import com.fml.entity.User;
import com.fml.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserController {
    @Autowired
    IUserService iUserService;

    @PostMapping("/test")
    public  String  insert(User user){

        long start=System.currentTimeMillis();
        System.out.println(iUserService.insert(user));
        long end=System.currentTimeMillis();
         return "success"+(end-start);
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
