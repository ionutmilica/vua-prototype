package vua.foundation;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import java.util.ArrayList;

public class Bootstrap {

    protected ArrayList<Module> modules;
    protected Injector injector;
    private StartApp startApp;

    public Bootstrap() {
        modules = new ArrayList<>();
    }

    public synchronized void boot() {

        if (injector != null) {
            throw new RuntimeException("Bootstrap already booted");
        }

        configure();

        initInjector();

        // Routes

        // Start framework
        Application app = injector.getInstance(Application.class);
        app.onStart();
    }

    public synchronized void shutdown() {
        injector.getInstance(Application.class).onShutdown();
        // stop the framework
        injector = null;
    }

    public void configure() {
        addModule(new AbstractModule() {
            @Override
            protected void configure() {
                install(new Modules());

                // Should be the last module
                install(startApp);
            }
        });
    }

    protected void addModule(Module module) {
        modules.add(module);
    }

    public Injector getInjector() {
        return injector;
    }

    private void initInjector() {
        this.injector = Guice.createInjector(modules);
    }

    public void setStartApp(StartApp startApp) {
        this.startApp = startApp;
    }
}
