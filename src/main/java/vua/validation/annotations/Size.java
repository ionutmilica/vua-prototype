package vua.validation.annotations;

import vua.validation.validators.SizeValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@WithValidator(SizeValidator.class)
@Target(ElementType.FIELD)
public @interface Size {
    int value();
    String key() default "size";
    String message() default "The %s size must be exactly %d.";
    String fieldKey() default "";
}
