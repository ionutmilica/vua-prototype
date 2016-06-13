package vua.validation;

import vua.validation.annotations.WithValidator;
import vua.utils.MessageBag;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
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

    public boolean passes() {
        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            Annotation[] annotations = field.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                WithValidator a = annotationType.getAnnotation(WithValidator.class);
                try {
                    vua.validation.validators.Validator<?> validator = a.value().newInstance();
                    boolean passes = validator.validate(params, fieldName);
                    if (!passes) {
                        String message = (String) annotationType.getMethod("message").invoke(annotation);
                        messages.add(fieldName, String.format(message, fieldName));
                    }
                } catch (Exception e) {
                    //
                }
            }

            System.out.println(Arrays.toString(annotations));
        }

        System.out.println(messages);

        return messages.isEmpty();
    }
}
