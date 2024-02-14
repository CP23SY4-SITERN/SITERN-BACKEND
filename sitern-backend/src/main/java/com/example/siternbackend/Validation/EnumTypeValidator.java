package com.example.siternbackend.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumTypeValidator implements ConstraintValidator<EnumValid, Enum<?>> {

    @Override
    public void initialize(EnumValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are handled by @NotNull
        }

        // Check if the enum constant exists
        try {
            Enum<?>[] enumValues = value.getDeclaringClass().getEnumConstants();
            for (Enum<?> enumValue : enumValues) {
                if (enumValue.name().equals(value.name())) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false; // Handle unexpected situations
        }
    }
}

