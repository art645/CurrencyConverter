package com.example.CurrencyConverter.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
@Entity
public class Currency {

    private final String id;
    private final int numcode;
    private final String charcode;
    private final int nominal;
    @Id
    private final String name;
    private final String value;
}
