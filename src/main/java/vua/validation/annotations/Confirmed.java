package vua.validation.annotations;

import vua.validation.validators.ConfirmedValidator;
import vua.validation.validators.SameValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@WithValidator(ConfirmedValidator.class)
@Target(ElementType.FIELD)
public @interface Confirmed {
    String key() default "matches";
    String message() default "%s was not confirmed by %s";
    String fieldKey() default "";
}
