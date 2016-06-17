package vua.validation.validators;

import vua.validation.Validator;
import vua.validation.annotations.Size;

import java.util.Collection;

public class SizeValidator implements RuleValidator<Object> {

    private final Size annotation;

    public SizeValidator(Size annotation) {
        this.annotation = annotation;
    }

    @Override
    public void validate(Validator validator, String field, Object value) {
        int size = annotation.value();

        if (value == null) {
            return;
        }

        boolean isEqual = false;

        if (value instanceof Collection) {
            isEqual = ((Collection) value).size() == size;
        } else if (value instanceof String) {
            isEqual = ((String) value).length() == size;
        } else if (value instanceof Object[]) {
            isEqual = ((Object[]) value).length == size;
        }

        if (!isEqual) {
            String message = String.format(annotation.message(), field, size);
            validator.addMessage(field, message);
        }
    }
}
