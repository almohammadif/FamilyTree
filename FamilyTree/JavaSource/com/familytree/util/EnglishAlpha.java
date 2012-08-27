package com.familytree.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Pattern(regexp="[\\sA-Za-z]+[\\d]*")
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})

public @interface EnglishAlpha {
	String message() default "{pattern.english.alpha}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	}
