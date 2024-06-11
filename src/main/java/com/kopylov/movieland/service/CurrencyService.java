package com.kopylov.movieland.service;

import com.kopylov.movieland.entity.CurrencyType;

public interface CurrencyService {

    default double convertFromUah(double price, CurrencyType toCurrency) {
        return convert(price, toCurrency);
    }

    double convert(double price, CurrencyType currencyType);
}