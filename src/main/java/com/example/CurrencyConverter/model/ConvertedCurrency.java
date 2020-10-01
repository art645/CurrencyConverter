package com.example.CurrencyConverter.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
@Data
@RequiredArgsConstructor
@Entity
@Table(name="converted_currency")
public class ConvertedCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp="^[0-9]*[.,]?[0-9]+$", message="Введите корректную сумму")
    private String amountBeforeExchange;
    private double amountAfterExchange;
    private String initCurrency;
    private String targetCurrency;
    private Date date;

    @PrePersist
    void createdAt() {
        this.date = new Date();
    }
}
