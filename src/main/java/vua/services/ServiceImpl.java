package vua.services;

import com.google.inject.*;
import com.google.inject.Inject;
import com.google.inject.Scope;
import com.google.inject.internal.ProviderMethod;
import com.google.inject.spi.DefaultBindingScopingVisitor;
import com.google.inject.spi.ProviderInstanceBinding;

public class ServiceImpl implements Service {
    private final Injector injector;
    private final ServiceRegister serviceRegister;
    private final ServiceSupport servieSupport;
    private volatile State state = State.STOPPED;
    private volatile long startTime;

    @Inject
    public ServiceImpl(Injector injector, ServiceRegister serviceRegister, ServiceSupport servieSupport) {
        this.serviceRegister = serviceRegister;
        this.injector = injector;
        this.servieSupport = servieSupport;
    }

    @Override
    public void start() {
        startTime = System.currentTimeMillis();
        System.out.println("Starting Vua application...");
        state = State.STARTING;

        for (final Binding binding : injector.getBindings().values()) {
            binding.acceptScopingVisitor(new DefaultBindingScopingVisitor() {
                public Object visitEagerSingleton() {
                    injector.getInstance(binding.getKey());
                    return null;
                }
                public Object visitScope(Scope scope) {
                    if (scope.equals(Scopes.SINGLETON)) {
                        Object target = injector.getInstance(binding.getKey());
                        if (binding instanceof ProviderInstanceBinding) {
                            javax.inject.Provider providerInstance = ((ProviderInstanceBinding) binding).getUserSuppliedProvider();
                            if (providerInstance instanceof ProviderMethod) {
                                if (servieSupport.hasServiceMethod(target.getClass())) {
                                    servieSupport.registerService(target);
                                }
                            }
                        }
                    }
                    return null;
                }

            });
        }
        serviceRegister.start();
        long time = System.currentTimeMillis() - startTime;
        System.out.printf("Vua application started in %d ms\n", time);
        state = serviceRegister.isStarted() ? State.STARTED : State.STOPPED;
    }

    @Override
    public void stop() {
        long start = System.currentTimeMillis();
        System.out.println("Stopping Vua application...");
        state = State.STOPPING;
        serviceRegister.stop();
        long time = System.currentTimeMillis() - start;
        System.out.printf("Vua application stopped in %d ms\n", time);
        state = serviceRegister.isStarted() ? State.STARTED : State.STOPPED;
    }

    @Override
    public boolean isStarted() {
        return serviceRegister.isStarted();
    }

    @Override
    public State getState() {
        return state;
    }

    public long getUpTime() {
        if (isStarted()) {
            return System.currentTimeMillis() - startTime;
        } else {
            return 0;
        }
    }
}