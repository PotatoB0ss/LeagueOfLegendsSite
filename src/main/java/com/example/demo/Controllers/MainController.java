package com.example.demo.Controllers;


import com.example.demo.login.AutheticationChecker;
import com.example.demo.mmrCheck.CheckMMR;
import com.example.demo.mmrCheck.DataMMR;
import com.example.demo.utility.ProductChecker;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path="/mb")
    public String miniBuy(Model model, @RequestParam("productNumber") String productName){
        autheticationChecker.authenticationCheck(model);
        ProductChecker productChecker = new ProductChecker();
        productChecker.productCheck(model, productName);
        return "modalBuy";
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
