package vua.servlet;

import com.google.inject.servlet.ServletModule;
import vua.foundation.Bootstrap;

public class ServletBootstrap extends Bootstrap {

    public void configure() {
        super.configure();

        // Add the servlet dispatcher and bind it to the dependency container
        addModule(new ServletModule() {
            public void configureServlets() {
                System.out.println("Configure servlet!");
                bind(ServletDispatcher.class).asEagerSingleton();
                serve("/*").with(ServletDispatcher.class);
            }
        });
    }
}
