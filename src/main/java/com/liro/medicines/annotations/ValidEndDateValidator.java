package com.liro.medicines.annotations;


import com.liro.medicines.model.dbentities.ApplicationRecord;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidEndDateValidator implements ConstraintValidator<ValidEndDate, ApplicationRecord> {

    @Override
    public boolean isValid(ApplicationRecord applicationRecord, ConstraintValidatorContext context) {
        if (applicationRecord.getApplicationDate() == null || applicationRecord.getEndDate() == null) {
            return true;
        }
        LocalDate maxEndDate = applicationRecord.getApplicationDate().plusDays(395);
        return !applicationRecord.getEndDate().isAfter(maxEndDate);
    }
}
