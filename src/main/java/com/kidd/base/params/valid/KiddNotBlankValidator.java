package com.kidd.base.params.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.kidd.base.annotation.KiddNotBlank;
import com.kidd.base.common.utils.KiddStringUtils;

/**
 * JiddNotBlankValidator.java
 *
 */
public class KiddNotBlankValidator implements
        ConstraintValidator<KiddNotBlank, String> {
    private String code;
    private String message;


    @Override
    public void initialize(KiddNotBlank constraintAnnotation) {
        code = constraintAnnotation.code();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String obj, ConstraintValidatorContext context) {
        if (KiddStringUtils.isBlank(obj)) {
            String messageTemplate = context.getDefaultConstraintMessageTemplate();
            messageTemplate = KiddStringUtils.isNotBlank(message) ? message : messageTemplate;
            context.disableDefaultConstraintViolation(); //禁用默认的message的值
            context.buildConstraintViolationWithTemplate(messageTemplate)
                    .addNode(code)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}