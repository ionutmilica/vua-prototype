package vua.validation.validators;

import vua.validation.Validator;
import vua.validation.annotations.Confirmed;

public class ConfirmedValidator implements RuleValidator<Object> {

    private final Confirmed annotation;

    public ConfirmedValidator(Confirmed annotation) {
        this.annotation = annotation;
    }

    @Override
    public void validate(Validator validator, String field) {
        String confirmField = String.format("%s_confirmation", field);
        String param = validator.getParamValue(field);
        String against = validator.getParamValue(confirmField);

        if (param != null) {
            if ( ! param.equals(against)) {
                String message = String.format(annotation.message(), field, confirmField);
                validator.addMessage(field, message);
            }
        }
    }
}
