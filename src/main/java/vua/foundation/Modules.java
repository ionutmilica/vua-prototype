package vua.foundation;

import com.google.inject.AbstractModule;
import vua.routing.RoutingModule;

public class Modules extends AbstractModule {
    @Override
    protected void configure() {
        install(new RoutingModule());
    }
}
