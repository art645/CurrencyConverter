package com.example.CurrencyConverter.controller;

import com.example.CurrencyConverter.dao.ConvertedCurrencyRepository;
import com.example.CurrencyConverter.dao.CurrencyRepository;
import com.example.CurrencyConverter.model.ConvertedCurrency;
import com.example.CurrencyConverter.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/convert")
public class CurrencyConverterController {

    private final CurrencyRepository currencyRepository;
    private final ConvertedCurrencyRepository convertedCurrencyRepository;
    private ConvertedCurrency valueTo;

    @Autowired
    public CurrencyConverterController(CurrencyRepository currencyRepository, ConvertedCurrencyRepository convertedCurrencyRepository) {
        this.currencyRepository = currencyRepository;
        this.convertedCurrencyRepository = convertedCurrencyRepository;
    }

    @GetMapping
    public String showCurrency(Model model) {
        List<Currency> currencies = new ArrayList<>();
        currencyRepository.findAll().forEach(i -> currencies.add(i));
            model.addAttribute("currencies",currencies);
            model.addAttribute("ConvertedCurrency", new ConvertedCurrency());
        return "convert";
    }

    @PostMapping
    public String showConvertationResult(@Valid ConvertedCurrency convertedCurrency, Errors errors) {
        if (errors.hasErrors()) {
            return "redirect:/convert";
        }
        Optional<Currency> initCurrency = currencyRepository.findById(convertedCurrency.getInitCurrency());
        Optional<Currency> targetCurrency = currencyRepository.findById(convertedCurrency.getTargetCurrency());
        double amountAfterExchange = exchangeCurrency(convertedCurrency);
        convertedCurrency.setAmountAfterExchange(amountAfterExchange);
        convertedCurrency.setInitCurrency(initCurrency.get().getName());
        convertedCurrency.setTargetCurrency(targetCurrency.get().getName());
        valueTo = convertedCurrency;
        convertedCurrencyRepository.save(convertedCurrency);
        return "redirect:/convert/result";
    }
    @GetMapping("/result")
    public String showConvertationResult(Model model) {
         model.addAttribute("ConvertationResult",valueTo);
        return "result";
    }
    public double exchangeCurrency(ConvertedCurrency convertedCurrency) {
        Optional<Currency> initCurrency = currencyRepository.findById(convertedCurrency.getInitCurrency());
        Optional<Currency> targetCurrency = currencyRepository.findById(convertedCurrency.getTargetCurrency());
        double initCurrencyValue = parseCurrencyValue(initCurrency.get().getValue());
        double targetCurrencyValue = parseCurrencyValue(targetCurrency.get().getValue());
        double initCourse = initCurrency.get().getNominal()/initCurrencyValue;
        double targetCourse = targetCurrency.get().getNominal()/targetCurrencyValue;
        double amountAfterExchange = Double.parseDouble(convertedCurrency.getAmountBeforeExchange()) * targetCourse / initCourse;
        return roundResult(amountAfterExchange);
    }
    public double parseCurrencyValue (String value) {
        return Double.parseDouble(value.replace(",","."));

    }
    public double roundResult(double amountAfterExchnage) {
        double b = Math.round(amountAfterExchnage * 1000);
        double c = b/1000;
        return c;
    }
}

