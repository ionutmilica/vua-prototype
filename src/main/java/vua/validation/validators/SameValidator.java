package vua.validation.validators;

import vua.validation.Validator;
import vua.validation.annotations.Same;

import java.util.regex.Pattern;

public class SameValidator implements RuleValidator<Object> {

    private final Same annotation;

    public SameValidator(Same annotation) {
        this.annotation = annotation;
    }

    @Override
    public void validate(Validator validator, String field) {
        String param = validator.getParamValue(field);
        String against = validator.getParamValue(annotation.as());

        if (param != null) {
            if ( ! param.equals(against)) {
                String message = String.format(annotation.message(), field, annotation.as());
                validator.addMessage(field, message);
            }
        }
    }
}
