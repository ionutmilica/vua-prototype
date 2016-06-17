package vua.validation.validators;

import vua.validation.Validator;
import vua.validation.annotations.Required;

public class RequiredValidator implements RuleValidator<Object> {

    private final Required annotation;

    public RequiredValidator(Required annotation) {
        this.annotation = annotation;
    }

    public void validate(Validator validator, String field, Object value) {
        String param = validator.getParamValue(field);

        if (param == null) {
            String message = String.format(annotation.message(), field);
            validator.addMessage(field, message);
        }
    }
}
