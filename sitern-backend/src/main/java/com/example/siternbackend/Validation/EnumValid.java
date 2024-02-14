package com.example.siternbackend.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumTypeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValid {
    Class<? extends Enum<?>> enumClass();

    String message() default "Invalid value. Accepted values are [{enumValues}]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


