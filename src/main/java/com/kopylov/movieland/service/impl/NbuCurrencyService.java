package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.entity.CurrencyType;
import com.kopylov.movieland.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class NbuCurrencyService implements CurrencyService {

    private final NbuRatesService nbuRatesService;

    @Override
    public double convert(double price, CurrencyType currencyType) {

        double exchangeRate = nbuRatesService.getExchangeRate(currencyType);

        return BigDecimal.valueOf(price / exchangeRate)
                .doubleValue();
    }
}