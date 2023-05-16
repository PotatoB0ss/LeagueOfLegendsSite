package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(path = "")
    public String main(){
        return "main";
    }

    @GetMapping(path = "euw")
    public String euw(){
        return "main";
    }

    @GetMapping(path="/register")
    public String login(){
        return "register";
    }




    @GetMapping(path ="reset")
    public String reset(){
        return "passwordReset/resetEmail";
    }


}
