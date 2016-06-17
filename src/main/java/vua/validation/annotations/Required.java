package vua.validation.annotations;

import vua.validation.validators.RequiredValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@WithValidator(RequiredValidator.class)
@Target(ElementType.FIELD)
public @interface Required {
    /**
     * The key for the violation message
     *
     * @return The key of the violation message
     */
    String key() default "required";

    /**
     * Default message if the key isn't found
     *
     * @return The default message
     */
    String message() default "The %s field is required.";

    /**
     * The key for formatting the field
     *
     * @return The key
     */
    String fieldKey() default "";
}
