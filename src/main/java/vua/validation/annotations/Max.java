package vua.validation.annotations;

import vua.validation.validators.MaxValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@WithValidator(MaxValidator.class)
@Target(ElementType.FIELD)
public @interface Max {
    int value();
    String key() default "size";
    String message() default "The %s size must be maximum %d.";
    String fieldKey() default "";
}
