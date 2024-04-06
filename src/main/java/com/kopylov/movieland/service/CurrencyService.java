package com.kopylov.movieland.service;

import com.kopylov.movieland.entity.CurrencyType;

public interface CurrencyService {
    double convertPrice(double price, CurrencyType currencyType);
}