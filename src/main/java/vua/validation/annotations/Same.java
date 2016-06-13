package vua.validation.annotations;

import vua.validation.validators.MatchesValidator;
import vua.validation.validators.SameValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@WithValidator(SameValidator.class)
@Target(ElementType.FIELD)
public @interface Same {
    String as();
    String key() default "matches";
    String message() default "%s doesn't match %s";
    String fieldKey() default "";
}
