package vua.view;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.rythmengine.RythmEngine;

public class RythmEngineProvider implements Provider<RythmEngine> {

    @Inject
    public RythmEngineProvider() {
    }

    @Override
    public RythmEngine get() {
        return new RythmEngine();
    }
}