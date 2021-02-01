package com.example.CurrencyConverter.configuration;


import com.example.CurrencyConverter.dao.CurrencyRepository;
import com.example.CurrencyConverter.model.Currency;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DownloadDailyCurrency {

    private String filePath = "C:\\javaProjects\\CurrencyConverter\\src\\main\\resources\\static\\XML_daily.asp";
    private List<Currency> currencies = new ArrayList<>();
    private CurrencyRepository currencyRepo;

    public DownloadDailyCurrency(CurrencyRepository currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    @Bean
    public void downloadXML() {
        URL url = null;
        try {
            url = new URL("https://www.cbr-xml-daily.ru/daily_utf8.xml");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Path targetPath = new File(filePath).toPath();
        try {
            Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Bean
    public void initSAXparser() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        try {
            XMLpars saxp = new XMLpars();
            parser.parse(new File("C:\\javaProjects\\CurrencyConverter\\src\\main\\resources\\static\\XML_daily.asp"), saxp);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Bean
    public void saveCurrencyInDB() {
        currencyRepo.saveAll(currencies);
    }

    private class XMLpars extends DefaultHandler {
        private Currency currency = new Currency();
        private String currentElement;


        @Override
        public void endDocument() throws SAXException {
            for (Currency currency : currencies) {
                System.out.println(currency.toString());
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentElement = qName;
            if (currentElement.equalsIgnoreCase("valute")) {
                String id = attributes.getValue("ID");
                currency.setId(id);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            currentElement = "";
            if (qName.equalsIgnoreCase("valute")) {
                System.out.println(currency.toString());
                currencies.add(currency);
                currency = new Currency();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            // получить ид из атрибутов
            if (currentElement.equals("NumCode")) {
                currency.setNumcode(Integer.valueOf(new String(ch, start, length)));
            }
            if (currentElement.equals("CharCode")) {
                currency.setCharcode(new String(ch, start, length));
            }
            if (currentElement.equals("Nominal")) {
                currency.setNominal(Integer.valueOf(new String(ch, start, length)));
            }
            if (currentElement.equals("Name")) {
                currency.setName(new String(ch, start, length));
            }
            if (currentElement.equals("Value")) {
                currency.setValue(new String(ch, start, length));
            }
        }
    }
}
