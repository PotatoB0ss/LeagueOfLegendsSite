package com.example.demo.purchase;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuccessfulPayment {
    //Номер платежа в их системе
    private final Integer id;
    //Номер платежа в моей системе
    private final Integer order_id;
    private final Float amount;
    private final Float in_amount;
    private final AdditionalData data;
    private final String createdDateTime;
    private final String status;
}
