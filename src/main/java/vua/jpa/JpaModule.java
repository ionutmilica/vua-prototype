package vua.jpa;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import vua.foundation.Config;

import java.util.Properties;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class JpaModule extends AbstractModule {

    private Config config;

    public JpaModule(Config config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        String persistenceUnitName = config.get("persistenceUnitName", "blog-dev");

        String noDatabase = config.get("noDatabase", "0");

        if (noDatabase.equals("1")) {
            return;
        }

        if (persistenceUnitName != null) {
            install(new JpaPersistModule(persistenceUnitName));

            UnitOfWorkInterceptor unitOfWorkInterceptor = new UnitOfWorkInterceptor();
            requestInjection(unitOfWorkInterceptor);

            // class-level @UnitOfWork
            bindInterceptor(annotatedWith(UnitOfWork.class), any(), unitOfWorkInterceptor);
            // method-level @UnitOfWork
            bindInterceptor(any(), annotatedWith(UnitOfWork.class), unitOfWorkInterceptor);

            bind(JpaInitializer.class).asEagerSingleton();
        }
    }
}