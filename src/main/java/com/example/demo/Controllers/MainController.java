package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(path = "/")
    public String base(){
        return "main";
    }

    @GetMapping(path = "main")
    public String euw(){
        return "main";
    }

    @GetMapping(path="/register")
    public String register(){
        return "register";
    }

    @GetMapping(path = "/login")
    public String login(){
        return "login";
    }




    @GetMapping(path ="reset")
    public String reset(){
        return "passwordReset/resetEmail";
    }


}
