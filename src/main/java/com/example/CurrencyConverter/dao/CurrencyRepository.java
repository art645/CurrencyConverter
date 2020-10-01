package com.example.CurrencyConverter.dao;

import com.example.CurrencyConverter.model.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency,String> {
}
