package com.example.CurrencyConverter.controller;

import com.example.CurrencyConverter.dao.ConvertedCurrencyRepository;
import com.example.CurrencyConverter.model.ConvertedCurrency;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/history")
public class ConvertationHistoryController {
    private final ConvertedCurrencyRepository convertedCurrencyRepository;

    public ConvertationHistoryController(ConvertedCurrencyRepository convertedCurrencyRepository) {
        this.convertedCurrencyRepository = convertedCurrencyRepository;
    }
    @GetMapping
    public String showConvertationHistory(Model model) {
        ArrayList <ConvertedCurrency> exchangeHistory = new ArrayList<>();
        convertedCurrencyRepository.findAll().forEach(i -> exchangeHistory.add(i));
        model.addAttribute("exchangeHistory",exchangeHistory);
        return "history";
    }
}
