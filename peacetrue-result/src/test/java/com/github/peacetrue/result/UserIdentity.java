package com.github.peacetrue.result;

import com.github.peacetrue.result.UserIdentityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiayx
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserIdentityValidator.class)
public @interface UserIdentity {

    String name() default "";

    String password() default "";

    String message() default "{com.github.peacetrue.result.constraints.UserIdentity.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}