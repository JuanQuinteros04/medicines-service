package com.liro.medicines.annotations;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidEndDateValidator.class)
@Target({ ElementType.TYPE }) // Se aplica a la clase completa, no a un solo campo
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEndDate {
    String message() default "La fecha de finalización no puede ser más de 365 días después de la fecha de aplicación";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}