package com.kidd.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.kidd.base.common.KiddErrorCodes;
import com.kidd.base.params.valid.KiddNotBlankValidator;

/**
 * 非空校验
 * 
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = KiddNotBlankValidator.class)
public @interface KiddNotBlank {
	String code() default KiddErrorCodes.E_KIDD_NULL;

	String message() default "field invalid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}