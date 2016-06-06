package vua.servlet;

import com.google.inject.servlet.ServletModule;
import vua.foundation.Bootstrap;

public class ServletBootstrap extends Bootstrap {

    public ServletBootstrap(Package appPk) {
        super(appPk);
    }

    public void configure() {
        super.configure();

        // Add the servlet dispatcher and bind it to the dependency container
        addModule(new ServletModule() {
            public void configureServlets() {
                bind(ServletDispatcher.class).asEagerSingleton();
                serve("/*").with(ServletDispatcher.class);
            }
        });
    }
}
