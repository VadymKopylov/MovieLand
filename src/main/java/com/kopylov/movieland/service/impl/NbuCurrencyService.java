package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.entity.CurrencyType;
import com.kopylov.movieland.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class NbuCurrencyService implements CurrencyService {

    private final WebClient nbuClient;

    @Override
    public double convertPrice(double price, CurrencyType currencyType) {
        if (currencyType == CurrencyType.UAH) {
            return price;
        }

        Double exchangeRate = getExchangeRate(currencyType);

        return convert(price, exchangeRate);
    }

    private Double getExchangeRate(CurrencyType currencyType) {
        String response = nbuClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("valcode", currencyType.getType()).queryParam("json").build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String rateString = response.split("\"rate\":")[1].split(",")[0];
        return Double.parseDouble(rateString);

    }

    private double convert(double moviePrice, double rate) {
        return BigDecimal.valueOf(moviePrice / rate)
                .setScale(2, RoundingMode.CEILING)
                .doubleValue();
    }
}