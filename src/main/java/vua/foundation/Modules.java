package vua.foundation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.multibindings.OptionalBinder;
import vua.routing.RoutingModule;
import vua.utils.ObjectMapperProvider;

public class Modules extends AbstractModule {
    @Override
    protected void configure() {
        install(new RoutingModule());

        bind(ObjectMapper.class).toProvider(ObjectMapperProvider.class).in(Singleton.class);

        /*
        OptionalBinder.newOptionalBinder(binder(), ObjectMapper.class)
                .setDefault().toProvider(ObjectMapperProvider.class).in(Singleton.class); */
    }
}
