package com.example.demo.purchase;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public
class AdditionalData {

    @SequenceGenerator(
            name = "additional_sequence",
            sequenceName = "additional_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "additional_sequence"
    )
    private Long id;
    private String prodName;
    private String userEmail;
    private String quantity;

    @Override
    public String toString() {
        return "AdditionalData{" +
                "prodName='" + prodName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }

    public AdditionalData(String prodName, String userEmail, String quantity) {
        this.prodName = prodName;
        this.userEmail = userEmail;
        this.quantity = quantity;
    }
}
