package vua.foundation;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import vua.contracts.AppRoutes;
import vua.routing.Router;

import java.util.ArrayList;

public class Bootstrap {

    protected ArrayList<Module> modules;
    protected Injector injector;
    protected Class startClass;

    public Bootstrap() {
        modules = new ArrayList<>();
    }

    public Bootstrap(Class startClass) {
        this();
        this.startClass = startClass;
    }

    public synchronized void boot() {

        if (injector != null) {
            throw new RuntimeException("Bootstrap already booted");
        }

        configure();

        initInjector();

        // AppRoutes

        try {
            Class routesClass = Class.forName(startClass.getPackage().getName()+".Routes");
            Router router = injector.getInstance(Router.class);
            AppRoutes routesInit = (AppRoutes) routesClass.newInstance();
            routesInit.init(router);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
}
