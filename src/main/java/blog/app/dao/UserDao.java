package blog.app.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import javax.persistence.EntityManager;

public class UserDao {

    @Inject
    private Provider<EntityManager> entityManagerProvider;

}
