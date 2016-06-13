package vua.view;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.rythmengine.RythmEngine;

public class ViewModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(RythmEngine.class).toProvider(RythmEngineProvider.class).in(Scopes.SINGLETON);
    }
}
