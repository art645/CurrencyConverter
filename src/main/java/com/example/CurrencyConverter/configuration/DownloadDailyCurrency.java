package com.example.CurrencyConverter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Configuration
public class DownloadDailyCurrency {
    @Bean
    public void downloadXml() {
        URL url = null;
        try {
            url = new URL("https://www.cbr-xml-daily.ru/daily_utf8.xml");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String fileName = "C:\\javaProjects\\CurrencyConverter\\src\\main\\resources\\static\\XML_daily.asp";
    Path targetPath = new File(fileName).toPath();
        try {
            Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
