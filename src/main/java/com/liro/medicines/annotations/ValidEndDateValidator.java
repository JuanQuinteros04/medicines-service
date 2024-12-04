package com.liro.medicines.annotations;


import com.liro.medicines.model.dbentities.ApplicationRecord;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidEndDateValidator implements ConstraintValidator<ValidEndDate, ApplicationRecord> {

    @Override
    public boolean isValid(ApplicationRecord applicationRecord, ConstraintValidatorContext context) {
        if (applicationRecord.getApplicationDate() == null || applicationRecord.getEndDate() == null) {
            return true; // No validamos si alguno de los campos es nulo
        }
        LocalDate maxEndDate = applicationRecord.getApplicationDate().plusDays(365);
        return !applicationRecord.getEndDate().isAfter(maxEndDate);
    }
}
