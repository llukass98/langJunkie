package ru.lukas.langjunkie.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dmitry Lukashevich
 */
@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "password must be 6 to 15 characters long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
