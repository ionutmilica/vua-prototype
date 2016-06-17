package vua.validation.annotations;

import vua.validation.validators.BetweenValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@WithValidator(BetweenValidator.class)
@Target(ElementType.FIELD)
public @interface Between {
    int min();
    int max();
    String key() default "between";
    String message() default "The %s size must be between %d and %d.";
    String fieldKey() default "";
}
