package vua.validation.annotations;

import vua.validation.validators.MinValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@WithValidator(MinValidator.class)
@Target(ElementType.FIELD)
public @interface Min {
    int value();
    String key() default "size";
    String message() default "The %s size must be minimum %d.";
    String fieldKey() default "";
}
