package com.familytree.util;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;


@Pattern(regexp="[\\sا-ي آأإلإلآئءؤ]+[\\d]*", message="{pattern.arabic.alpha}")
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})

public @interface ArabicAlpha {
	String message() default "{pattern.arabic.alpha}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	}
