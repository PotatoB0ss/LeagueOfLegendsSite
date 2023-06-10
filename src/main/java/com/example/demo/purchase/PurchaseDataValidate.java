package com.example.demo.purchase;

import java.math.BigDecimal;
import java.util.Map;

public class PurchaseDataValidate {

    public String dataCheck(Map<String, Object> product){

        int amount = Integer.parseInt( product.get("amount").toString());
        if(0 >= amount){
            return "Error : amount can`t be less than 1";
        }

        BigDecimal quantity = new BigDecimal(product.get("amount").toString());
        BigDecimal price = new BigDecimal(product.get("prodPrice").toString().replace("$", ""));
        BigDecimal finalPrice = price.multiply(quantity);
        double forCheck = finalPrice.doubleValue();
        if(forCheck < 1.40){
            return "Error: minimum purchase sum is 1.40$";
        }

        if(Integer.parseInt(product.get("stock").toString()) < amount){
            return "Sorry accounts in stock are " + Integer.parseInt(product.get("stock").toString());
        }

        BigDecimal exchangeRate = new BigDecimal("80.00");
        BigDecimal multiplyPrice = finalPrice.multiply(exchangeRate);
        Float finalRUBPrice = multiplyPrice.floatValue();
        product.put("finalRUBPrice", finalRUBPrice);


        return "successfully";
    }



}
