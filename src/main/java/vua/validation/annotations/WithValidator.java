package vua.validation.annotations;

import vua.validation.validators.RuleValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface WithValidator {
    /**
     * Validator that should be used to validate parameters annotated with this
     * annotation.
     *
     * @return The validator class
     */
    Class<? extends RuleValidator<?>> value();
}
