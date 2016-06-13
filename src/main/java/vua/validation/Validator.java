package vua.validation;

import vua.utils.StringConverter;
import vua.validation.annotations.WithValidator;
import vua.utils.MessageBag;
import vua.validation.validators.RuleValidator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

public class Validator<T> {

    private T bean;
    private Map<String, String[]> params;
    private MessageBag messages;

    public Validator() {
        messages = new MessageBag();
    }

    public Validator(T bean, Map<String, String[]> params) {
        this();
        this.bean = bean;
        this.params = params;
    }

    public String[] getParam(String key) {
        return params.get(key);
    }

    public String getParamValue(String key) {
        String[] values = params.get(key);
        return values != null && values.length > 0 ? values[0] : null;
    }

    public boolean passes() {
        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            Annotation[] annotations = field.getDeclaredAnnotations();

            try {
                String[] tmp = params.get(fieldName);
                String param = tmp != null && tmp.length > 0 ? tmp[0] : null;
                Object value = StringConverter.convert(param, field.getType());
                if (value != null) {
                    field.set(bean, value);
                }
            } catch (Exception e) {
                //
                e.printStackTrace();
                messages.add(fieldName, String.format("%s cannot convert to %s type", fieldName, field.getType().getSimpleName()));
            }

            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                WithValidator a = annotationType.getAnnotation(WithValidator.class);
                try {
                    Constructor constructor = a.value().getConstructor(annotationType);
                    RuleValidator<?> validator = (RuleValidator<?>) constructor.newInstance(annotation);
                    validator.validate(this, fieldName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return messages.isEmpty();
    }

    public void addMessage(String key, String message) {
        messages.add(key, message);
    }
}
