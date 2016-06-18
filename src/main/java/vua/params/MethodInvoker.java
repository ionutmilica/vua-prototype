package vua.params;

import com.google.inject.Injector;
import vua.http.Context;
import vua.http.Request;
import vua.params.Extractors.BaseArgumentExtractor;
import vua.params.Extractors.FormRequestExtractor;
import vua.params.Extractors.RequestExtractor;
import vua.validation.FormRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class MethodInvoker {

    private Method method;
    private BaseArgumentExtractor<?>[] argumentExtractors;

    private static HashMap<Class<?>, Class<? extends BaseArgumentExtractor>> EXTRACTORS;

    public MethodInvoker(Method method, BaseArgumentExtractor<?>[] argumentExtractors) {
        this.method = method;
        this.argumentExtractors = argumentExtractors;
    }

    /**
     * It receives a controller and the context (that has the global state of the app)
     * and then it invokes the action.
     * @Todo: Mark the method with throws and provide ways to handle the exceptions
     *
     * @param controller
     * @param context
     * @return
     */
    public Object invoke(Object controller, Context context) {
        Object arguments[] = new Object[argumentExtractors.length];

        for (int i = 0; i < argumentExtractors.length; i++) {
            arguments[i] = argumentExtractors[i].extract(context);
        }

        try {
            return method.invoke(controller, arguments);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Build the method MethodInvoker
     *
     * @param method
     * @param injector
     * @return
     */
    public static MethodInvoker build(Method method, Injector injector) {
        Class[] paramTypes = method.getParameterTypes();
        BaseArgumentExtractor<?>[] argumentExtractors = new BaseArgumentExtractor<?>[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            try {
                Class<?> type = paramTypes[i];
                Class<? extends BaseArgumentExtractor> argument = getExtractorForType(type);

                if (argument == null) {
                    argument = getExtractorForType(type.getSuperclass());
                }

                if (argument == null) {
                    throw  new RuntimeException("Cannot bind non bindable object!");
                }

                argumentExtractors[i] = injector.getInstance(argument);
                argumentExtractors[i].setConcreteClass(type);
            } catch (RuntimeException e) {
                throw new RuntimeException("Cannot bind non bindable object!");
            }
        }

        return new MethodInvoker(method, argumentExtractors);
    }

    public static Class<? extends BaseArgumentExtractor> getExtractorForType(Class<?> type) {
        if (EXTRACTORS == null) {
            EXTRACTORS = new HashMap<>();
            EXTRACTORS.put(Request.class, RequestExtractor.class);
            EXTRACTORS.put(FormRequest.class, FormRequestExtractor.class);
        }
        return EXTRACTORS.get(type);
    }
}
