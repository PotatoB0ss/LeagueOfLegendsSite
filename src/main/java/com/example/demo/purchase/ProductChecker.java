package com.example.demo.purchase;

import com.example.demo.accounts.AccountService;
import org.springframework.ui.Model;

public class ProductChecker {

    private final AccountService accountService;

    public ProductChecker(AccountService accountService) {
        this.accountService = accountService;
    }

    public Model productCheck(Model model, String productName){

        accountService.getAccountsCount(model);

        switch (productName){
            case "first":
                model.addAttribute("prodName", "RU | Active | 30+");
                model.addAttribute("prodImg", "/assets/img/Teemo_0.jpg");
                model.addAttribute("prodPrice", "$0.50");
                model.addAttribute("prodStock", model.getAttribute("ru_active"));
                return model;
            case "second":
                model.addAttribute("prodName", "RU | Inactive | 30+");
                model.addAttribute("prodImg", "/assets/img/teemos.jpg");
                model.addAttribute("prodPrice", "$1.00");
                model.addAttribute("prodStock", model.getAttribute("ru_inactive"));
                return model;
            case "third":
                model.addAttribute("prodName", "RU | IRON-BRONZE");
                model.addAttribute("prodImg", "/assets/img/Teemo_1.jpg");
                model.addAttribute("prodPrice", "$0.70");
                model.addAttribute("prodStock", model.getAttribute("ru_iron_bronze"));
                return model;
            case "fourth":
                model.addAttribute("prodName", "RU | SILVER");
                model.addAttribute("prodImg", "/assets/img/maxresdefault.jpg");
                model.addAttribute("prodPrice", "$1.00");
                model.addAttribute("prodStock", model.getAttribute("ru_silver"));
                return model;
            case "fifth":
                model.addAttribute("prodName", "RU | GOLD");
                model.addAttribute("prodImg", "/assets/img/Teemo_18.jpg");
                model.addAttribute("prodPrice", "$2.00");
                model.addAttribute("prodStock", model.getAttribute("ru_gold"));
                return model;
            case "sixth":
                model.addAttribute("prodName", "RU | PLATINUM");
                model.addAttribute("prodImg", "/assets/img/Teemo_25.jpg");
                model.addAttribute("prodPrice", "$3.00");
                model.addAttribute("prodStock", model.getAttribute("ru_platinum"));
                return model;
            case "seventh":
                model.addAttribute("prodName", "EU | Active | 30+");
                model.addAttribute("prodImg", "/assets/img/Teemo_0.jpg");
                model.addAttribute("prodPrice", "$0.25");
                model.addAttribute("prodStock", model.getAttribute("eu_active"));
                return model;
            case "eighth":
                model.addAttribute("prodName", "EU | Inactive | 30+");
                model.addAttribute("prodImg", "/assets/img/teemos.jpg");
                model.addAttribute("prodPrice", "$0.50");
                model.addAttribute("prodStock", model.getAttribute("eu_inactive"));
                return model;
            case "ninth":
                model.addAttribute("prodName", "EU | IRON-BRONZE");
                model.addAttribute("prodImg", "/assets/img/Teemo_1.jpg");
                model.addAttribute("prodPrice", "$0.50");
                model.addAttribute("prodStock", model.getAttribute("eu_iron_bronze"));
                return model;
            case "tenth":
                model.addAttribute("prodName", "EU | SILVER");
                model.addAttribute("prodImg", "/assets/img/maxresdefault.jpg");
                model.addAttribute("prodPrice", "$0.70");
                model.addAttribute("prodStock", model.getAttribute("eu_silver"));
                return model;
            case "eleventh":
                model.addAttribute("prodName", "EU | GOLD");
                model.addAttribute("prodImg", "/assets/img/Teemo_18.jpg");
                model.addAttribute("prodPrice", "$1.50");
                model.addAttribute("prodStock", model.getAttribute("eu_gold"));
                return model;
            case "twelfth":
                model.addAttribute("prodName", "EU | PLATINUM");
                model.addAttribute("prodImg", "/assets/img/Teemo_25.jpg");
                model.addAttribute("prodPrice", "$3.00");
                model.addAttribute("prodStock", model.getAttribute("eu_platinum"));
                return model;
        }
        return model;
    }


}
