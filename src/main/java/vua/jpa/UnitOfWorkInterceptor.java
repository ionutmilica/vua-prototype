package vua.jpa;

import com.google.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class UnitOfWorkInterceptor implements MethodInterceptor {

    @Inject
    private com.google.inject.persist.UnitOfWork unitOfWork;

    private final ThreadLocal<Boolean> workWasStarted = new ThreadLocal<>();

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (null == workWasStarted.get()) {
            unitOfWork.begin();
            workWasStarted.set(Boolean.TRUE);
        } else {
            return invocation.proceed();
        }

        try {
            return invocation.proceed();
        } finally {
            if (null != workWasStarted.get()) {
                workWasStarted.remove();
                unitOfWork.end();
            }
        }
    }
}