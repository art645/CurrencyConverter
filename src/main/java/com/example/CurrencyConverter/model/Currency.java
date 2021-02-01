package com.example.CurrencyConverter.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
@Entity
public class Currency {
    private String id;
    private int numcode;
    private String charcode;
    private int nominal;
    @Id
    private String name;
    private String value;
}
