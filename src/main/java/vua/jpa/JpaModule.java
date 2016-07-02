package vua.jpa;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import java.util.Properties;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

public class JpaModule extends AbstractModule {

    public JpaModule() {
    }

    @Override
    protected void configure() {
        String persistenceUnitName = "blog-dev";

        if (persistenceUnitName != null) {
            String connectionUrl = null;
            String connectionUsername = null;
            String connectionPassword = null;

            Properties jpaProperties = new Properties();

            if (connectionUrl != null) {
                jpaProperties.put("hibernate.connection.url", connectionUrl);
            }

            if (connectionUsername != null) {
                jpaProperties.put("hibernate.connection.username", connectionUsername);
            }

            if (connectionPassword != null) {
                jpaProperties.put("hibernate.connection.password", connectionPassword);
            }

            install(new JpaPersistModule(persistenceUnitName).properties(jpaProperties));

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