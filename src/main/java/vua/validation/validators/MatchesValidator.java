package vua.validation.validators;

import vua.validation.Validator;
import vua.validation.annotations.Matches;
import java.util.regex.Pattern;

public class MatchesValidator implements RuleValidator<Object> {

    private final Matches annotation;
    private final Pattern pattern;

    public MatchesValidator(Matches annotation) {
        this.annotation = annotation;
        this.pattern = Pattern.compile(annotation.regexp());
    }

    @Override
    public void validate(Validator validator, String field, Object value) {
        String param = validator.getParamValue(field);

        if (param != null) {
            if ( ! this.pattern.matcher(param).matches()) {
                String message = String.format(annotation.message(), field, annotation.regexp());
                validator.addMessage(field, message);
            }
        }
    }
}
