package vua.validation.validators;

import vua.validation.Validator;
import vua.validation.annotations.Between;

import java.util.Collection;

public class BetweenValidator implements RuleValidator<Object> {

    private final Between annotation;

    public BetweenValidator(Between annotation) {
        this.annotation = annotation;
    }

    @Override
    public void validate(Validator validator, String field, Object value) {
        int sizeOf = 0;
        int min = annotation.min(), max = annotation.max();

        if (value == null) {
            return;
        }

        if (value instanceof Collection) {
            sizeOf = ((Collection) value).size();
        } else if (value instanceof String) {
            sizeOf = ((String) value).length();
        } else if (value instanceof Object[]) {
            sizeOf = ((Object[]) value).length;
        } else if (value instanceof Integer) {
            sizeOf = (Integer) value;
        }

        if (sizeOf < min || sizeOf > max) {
            String message = String.format(annotation.message(), field, min, max);
            validator.addMessage(field, message);
        }
    }
}
