package com.kopylov.movieland.configuration.converter;

import com.kopylov.movieland.entity.SortOrder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverter implements Converter<String, SortOrder> {

    @Override
    public SortOrder convert(String source) {
        try {
            return SortOrder.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}