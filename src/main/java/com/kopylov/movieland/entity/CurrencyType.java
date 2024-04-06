package com.kopylov.movieland.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencyType {

    USD("USD"),
    EUR("EUR"),
    UAH("UAH");

    private final String type;
}