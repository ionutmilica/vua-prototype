package blog.app.services;

import blog.app.dao.SetupDao;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import vua.services.Start;

@Singleton
public class SetupService {

    @Inject
    private SetupDao setupDao;

    @Start
    public void start() {
        setupDao.setup();
    }
}
