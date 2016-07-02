package vua.jpa;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import vua.services.Start;
import vua.services.Stop;

@Singleton
public class JpaInitializer {
    private PersistService persistService;

    @Inject
    JpaInitializer(PersistService persistService) {
        this.persistService = persistService;
    }


    @Start(order = 10)
    public void start() {
        persistService.start();
    }

    @Stop(order = 10)
    public void stop() {
        persistService.stop();
    }
}