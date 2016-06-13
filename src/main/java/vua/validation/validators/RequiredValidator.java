package vua.validation.validators;

import java.util.Map;

public class RequiredValidator implements Validator<Object> {

    @Override
    public boolean validate(Map<String, String[]> data, String field) {


        return false;
    }

    @Override
    public Class<Object> getValidatedType() {
        return null;
    }
}
