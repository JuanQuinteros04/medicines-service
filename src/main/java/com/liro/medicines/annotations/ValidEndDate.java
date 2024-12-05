package com.liro.medicines.annotations;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidEndDateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEndDate {
    String message() default "The end date cannot be more than 365 days.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}