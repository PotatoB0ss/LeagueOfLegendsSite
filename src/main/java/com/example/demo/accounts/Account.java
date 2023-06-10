package com.example.demo.accounts;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Account {


    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )

    private Long id;
    @Column(unique = true)
    private String data;
    @Enumerated(EnumType.STRING)
    private AccountRegion accountRegion;
    @Enumerated(EnumType.STRING)
    private AccountCategory accountCategory;
    @Column(nullable = true)
    private Long orderId;

    public Account(Long id, String data, AccountRegion accountRegion, AccountCategory accountCategory) {
        this.id = id;
        this.data = data;
        this.accountRegion = accountRegion;
        this.accountCategory = accountCategory;
    }

    public Account(String data, AccountRegion accountRegion, AccountCategory accountCategory) {
        this.data = data;
        this.accountRegion = accountRegion;
        this.accountCategory = accountCategory;
    }
}
