package vua.validation.annotations;

import vua.validation.validators.MatchesValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@WithValidator(MatchesValidator.class)
@Target(ElementType.FIELD)
public @interface Matches {
    String regexp();
    String key() default "matches";
    String message() default "%s doesn't match the format %s";
    String fieldKey() default "";
}
