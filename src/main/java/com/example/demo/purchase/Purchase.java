package com.example.demo.purchase;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Purchase {
    private final String userName;
    private final String userEmail;
    private final Integer numberInput;
    private final Integer stock;
    private final String prodPrice;
    private final String prodName;
}