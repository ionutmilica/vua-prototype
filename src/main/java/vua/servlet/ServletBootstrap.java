package vua.servlet;

import com.google.inject.servlet.ServletModule;
import vua.foundation.Bootstrap;

public class ServletBootstrap extends Bootstrap {

    public ServletBootstrap(Class startClass) {
        super(startClass);
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
