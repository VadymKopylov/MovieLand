package com.kopylov.movieland.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kopylov.movieland.entity.CurrencyType;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class NbuRatesService {

    private final WebClient nbuClient;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final Map<CurrencyType, Double> exchangeRates = new ConcurrentHashMap<>();

    public double getExchangeRate(CurrencyType currencyType) {
        return exchangeRates.get(currencyType);
    }

    @PostConstruct
    @Scheduled(cron = "${nbu.currency.invalidate}")
    private void loadExchangeRates() throws JsonProcessingException {

        String dateString = LocalDate.now().format(formatter);
        String dateParam = "?date=" + dateString + "&json";

        String response = nbuClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam(dateParam).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode ratesNode = rootNode.get("rates");

        if (ratesNode != null && ratesNode.isArray()) {
            for (JsonNode rateNode : ratesNode) {
                String valCode = rateNode.get("cc").asText();
                Double rate = rateNode.get("rate").asDouble();

                CurrencyType currencyType = CurrencyType.valueOf(valCode);
                exchangeRates.put(currencyType, rate);
            }
        }
    }
}