package com.demo.common.valid;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.demo.common.valid.validator.TypeValidator;


/**
 * 自定义枚举类型注解
 * @author baiqiufei
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = TypeValidator.class)
@Documented
public @interface CheckType {

    String message() default "枚举类型校验错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    Class<?> type();

//    boolean nullable() default true;
}