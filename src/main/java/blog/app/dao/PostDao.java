package blog.app.dao;

import blog.app.models.Post;
import com.google.inject.Inject;
import com.google.inject.Provider;
import javafx.geometry.Pos;
import vua.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class PostDao {

    @Inject
    private Provider<EntityManager> entityManagerProvider;

    @UnitOfWork
    public List<Post> getPosts() {
        EntityManager entityManager = entityManagerProvider.get();
        TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post p", Post.class);
        return query.getResultList();
    }
}
