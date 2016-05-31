package vua.routing;

import com.google.inject.AbstractModule;

public class RoutingModule extends AbstractModule {

    public void configure() {
        System.out.println("Routing");
        bind(Router.class).asEagerSingleton();
    }
}
