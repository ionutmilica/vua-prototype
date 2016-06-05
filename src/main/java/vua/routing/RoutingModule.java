package vua.routing;

import com.google.inject.AbstractModule;

public class RoutingModule extends AbstractModule {

    public void configure() {
        bind(Router.class).asEagerSingleton();
    }
}
