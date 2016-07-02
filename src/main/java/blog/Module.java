package blog;

import blog.app.services.SetupService;
import com.google.inject.AbstractModule;

public class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(SetupService.class);
    }
}
