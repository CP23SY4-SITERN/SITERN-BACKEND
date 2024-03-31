package com.example.siternbackend.Validation;

import com.example.siternbackend.Exception.EnumValidationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {
    private Enum<?>[] enumValues;

    @Override
    public void initialize(EnumValid annotation) {
        Class<? extends Enum<?>> enumClass = annotation.enumClass();
        enumValues = enumClass.getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are validated by @NotNull
        }

        boolean isValid = Arrays.stream(enumValues)
                .anyMatch(enumValue -> enumValue.name().equals(value));

        if (!isValid) {
            throw new EnumValidationException("Invalid workType value. Accepted values are: " + Arrays.toString(enumValues));
        }

        return true;
    }
}

