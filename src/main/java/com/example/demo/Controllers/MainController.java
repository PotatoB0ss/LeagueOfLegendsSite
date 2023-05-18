package com.example.demo.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(path = "main")
    public String basePage(Authentication authentication, Model model){
        System.out.println(authentication);
        if(authentication != null && authentication.isAuthenticated()){
            model.addAttribute("authenticated", true);
        }else{
            model.addAttribute("authenticated", false);
        }
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
