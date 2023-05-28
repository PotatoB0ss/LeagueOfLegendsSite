package com.example.demo.utility;

import org.springframework.ui.Model;

public class ProductChecker {

    public Model productCheck(Model model, String productName){
        switch (productName){
            case "first":
                model.addAttribute("prodName", "RU | Active | 30+");
                model.addAttribute("prodImg", "/assets/img/Teemo_0.jpg");
                model.addAttribute("prodPrice", "$0.50");
                return model;
            case "second":
                model.addAttribute("prodName", "RU | Inactive | 30+");
                model.addAttribute("prodImg", "/assets/img/teemos.jpg");
                model.addAttribute("prodPrice", "$1.00");
                return model;
            case "third":
                model.addAttribute("prodName", "RU | IRON-BRONZE");
                model.addAttribute("prodImg", "/assets/img/Teemo_1.jpg");
                model.addAttribute("prodPrice", "$0.70");
                return model;
            case "fourth":
                model.addAttribute("prodName", "RU | SILVER");
                model.addAttribute("prodImg", "/assets/img/maxresdefault.jpg");
                model.addAttribute("prodPrice", "$1.00");
                return model;
            case "fifth":
                model.addAttribute("prodName", "RU | GOLD");
                model.addAttribute("prodImg", "/assets/img/Teemo_18.jpg");
                model.addAttribute("prodPrice", "$2.00");
                return model;
            case "sixth":
                model.addAttribute("prodName", "RU | PLATINUM");
                model.addAttribute("prodImg", "/assets/img/Teemo_25.jpg");
                model.addAttribute("prodPrice", "$3.00");
                return model;
        }
        return model;
    }


}
