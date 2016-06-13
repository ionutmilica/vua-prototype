package vua.validation.validators;

import vua.validation.Validator;

public interface RuleValidator<T> {
    void validate(Validator data, String field);
}
