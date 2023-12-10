package com.example.siternbackend.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EnumConverter implements Converter<String, Enum<?>> {

    @Override
    public Enum<?> convert(String source) {
        // Implement the logic to convert a string to the corresponding enum
        // You might need to handle case sensitivity and other variations
        throw new UnsupportedOperationException("Enum conversion not implemented");
    }
}

