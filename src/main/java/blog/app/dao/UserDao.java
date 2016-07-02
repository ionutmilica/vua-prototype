package blog.app.dao;

import blog.app.models.Post;
import com.google.inject.Inject;
import com.google.inject.Provider;
import vua.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDao {

    @Inject
    private Provider<EntityManager> entityManagerProvider;

}
