package vua.validation.validators;

import vua.validation.Validator;

public interface RuleValidator<T> {
    void validate(Validator validator, String field, Object value);
}
