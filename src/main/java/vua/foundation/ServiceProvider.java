package vua.foundation;

import com.google.inject.AbstractModule;

public abstract class ServiceProvider extends AbstractModule {

    /**
     * Configure is used to register the dependencies
     */
    public void configure() {}

    /**
     * Every service provider should be booted by the application after the dependencies are binded
     */
    public abstract void boot();

    /**
     * On framework shutdown for every provider the shutdown method will be called
     */
    public void shutdown() {}

}
