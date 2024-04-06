package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.entity.CurrencyType;
import com.kopylov.movieland.service.CurrencyService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DefaultCurrencyService implements CurrencyService {

    private static final String NBU_URI = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange";
    private final WebClient nbuClient = WebClient.create(NBU_URI);

    @Override
    public double convertPrice(double price, CurrencyType currencyType) {
        if (currencyType == CurrencyType.UAH) {
            return price;
        }

        Double exchangeRate = getExchangeRate(currencyType);

        if (exchangeRate == null) {
            throw new IllegalStateException("Exchange rate is null");
        }

        return convert(price, exchangeRate);
    }

    private Double getExchangeRate(CurrencyType currencyType) {
        String response = nbuClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("valcode", currencyType.getType()).queryParam("json").build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (response != null) {
            String rateString = response.split("\"rate\":")[1].split(",")[0];
            return Double.parseDouble(rateString);
        } else {
            return null;
        }
    }

    private double convert(double moviePrice, double rate) {
        return BigDecimal.valueOf(moviePrice / rate)
                .setScale(2, RoundingMode.CEILING)
                .doubleValue();
    }
}