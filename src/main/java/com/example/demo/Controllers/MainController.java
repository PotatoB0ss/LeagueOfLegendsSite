package com.example.demo.Controllers;


import com.example.demo.appuser.AppUser;
import com.example.demo.login.AutheticationChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class MainController {

    AutheticationChecker autheticationChecker = new AutheticationChecker();

    @GetMapping(path = "main")
    public String basePage(Model model) {
        autheticationChecker.authenticationCheck(model);
        return "main";
    }

    @GetMapping(path="/register")
    public String register(Model model){
        if (autheticationChecker.authenticationCheck(model)) {
            return "redirect:/main";
        }
        return "register";
    }

    @GetMapping(path = "/login")
    public String login(Model model){
        if (autheticationChecker.authenticationCheck(model)) {
            return "redirect:/main";
        }
        return "/login";
    }

    @GetMapping(path = "/logout")
    public String logout(){
        return "main";
    }


    @GetMapping(path ="reset")
    public String reset(){
        return "passwordReset/resetEmail";
    }


}
