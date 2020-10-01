package com.example.CurrencyConverter.dao;

import com.example.CurrencyConverter.model.ConvertedCurrency;
import org.springframework.data.repository.CrudRepository;

public interface ConvertedCurrencyRepository extends CrudRepository<ConvertedCurrency,Long> {
}
