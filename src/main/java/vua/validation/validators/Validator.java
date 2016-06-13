package vua.validation.validators;

import java.util.Map;

public interface Validator<T> {

    boolean validate(Map<String, String[]> data, String field);

    Class<T> getValidatedType();
}
