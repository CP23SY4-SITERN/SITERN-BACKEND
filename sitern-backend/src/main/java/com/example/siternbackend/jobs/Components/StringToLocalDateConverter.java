package com.example.siternbackend.jobs.Components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        // Adjust the date format based on the actual format in your DTO
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(source, formatter);
    }
}

