package com.example.demo.purchase;


import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
class PaymentData {

    @SequenceGenerator(
            name = "payment_sequence",
            sequenceName = "payment_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_sequence"
    )
    private Long id;

    private Integer shop_id;
    private Integer order_id;
    private Float amount;
    private String token;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "additional_data_id")
    private AdditionalData additionalData;

    public PaymentData(Integer shop_id, Integer order_id, Float amount, String token, AdditionalData additionalData) {
        this.shop_id = shop_id;
        this.order_id = order_id;
        this.amount = amount;
        this.token = token;
        this.additionalData = additionalData;
    }
}
