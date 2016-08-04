package vua.foundation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.OptionalBinder;
import vua.jpa.JpaModule;
import vua.routing.RoutingModule;
import vua.services.ServiceSupport;
import vua.utils.ObjectMapperProvider;

public class Modules extends AbstractModule {

    private Config config;

    public Modules(Config config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        install(new RoutingModule());
        install(ServiceSupport.getModule());

        OptionalBinder.newOptionalBinder(binder(), ObjectMapper.class)
                .setDefault().toProvider(ObjectMapperProvider.class).in(Singleton.class);

        install(new JpaModule(config));
    }
}
