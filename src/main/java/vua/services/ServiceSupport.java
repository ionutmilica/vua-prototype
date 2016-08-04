package vua.services;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import java.lang.reflect.Method;

public class ServiceSupport {
    private final ServiceRegister serviceRegistry = new ServiceRegister();

    public static Module getModule() {
        return new ServiceSupport().constructModule();
    }

    private ServiceSupport() {
    }

    private class ServiceAnnotatedListener implements TypeListener {
        @Override
        public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
            if (hasServiceMethod(type.getRawType())) {
                encounter.register(new ServiceListener<I>());
            }
        }
    }

    private class ServiceListener<I> implements InjectionListener<I> {
        @Override
        public void afterInjection(final I injectee) {
            registerService(injectee);
        }
    }

    public boolean hasServiceMethod(Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.getAnnotation(Start.class) != null || method.getAnnotation(Stop.class) != null) {
                return true;
            }
        }
        return false;
    }

    public void registerService(Object target) {
        for (final Method method : target.getClass().getMethods()) {
            Start start = method.getAnnotation(Start.class);
            if (start != null) {
                serviceRegistry.registerStartable(new Target(method, target, start.order()));
            }
            Stop dispose = method.getAnnotation(Stop.class);
            if (dispose != null) {
                serviceRegistry.registerDisposable(new Target(method, target, dispose.order()));
            }
        }
    }

    private Module constructModule() {
        return new AbstractModule() {
            protected void configure() {
                bindListener(Matchers.any(), new ServiceAnnotatedListener());
                bind(ServiceRegister.class).toInstance(serviceRegistry);
                bind(ServiceSupport.class).toInstance(ServiceSupport.this);
            }
        };
    }


}