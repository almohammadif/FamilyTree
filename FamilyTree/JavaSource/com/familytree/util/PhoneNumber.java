package com.familytree.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;


@Pattern(regexp="^$|[\\d\\s]+", message="{pattern.phone}")
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})

public @interface PhoneNumber {
	String message() default "{pattern.phone}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	}

