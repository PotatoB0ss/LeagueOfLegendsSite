package com.example.demo.Controllers;


import com.example.demo.login.AutheticationChecker;
import com.example.demo.mmrCheck.CheckMMR;
import com.example.demo.mmrCheck.DataMMR;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


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
        return "loggin";
    }

    @GetMapping(path = "/logout")
    public String logout(){
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/main";
    }


    @GetMapping(path ="reset")
    public String reset(Model model){
        if (autheticationChecker.authenticationCheck(model)) {
            return "redirect:/main";
        }
        return "passwordReset/passwordRecovery";
    }

    @GetMapping(path = "/mmrCheck")
    public String checkMmr(Model model){
        autheticationChecker.authenticationCheck(model);
        return "mmrChecker";
    }


    @PostMapping("/mmrChecks")
    @ResponseBody
    public Map<String, Object> checkMmrRequest(Model model, @RequestBody DataMMR dataMMR){
        autheticationChecker.authenticationCheck(model);
        Map<String, Object> response = new HashMap<>();

        CheckMMR checkMMR = new CheckMMR();
        response.put("uss", checkMMR.inputData(dataMMR.getUsername(), dataMMR.getRegion(), response));



        return response;

    }


}
