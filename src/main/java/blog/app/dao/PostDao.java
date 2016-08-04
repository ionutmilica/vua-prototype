package blog.app.dao;

import blog.app.models.Post;
import com.google.inject.Inject;
import com.google.inject.Provider;
import vua.jpa.UnitOfWork;

import javax.persistence.EntityManager;
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

    @UnitOfWork
    public Post postById(long id) {
        EntityManager entityManager = entityManagerProvider.get();
        String sql = "SELECT p FROM Post p WHERE id=:id";

        TypedQuery<Post> query = entityManager.createQuery(sql, Post.class)
                .setParameter("id", id);

        List<Post> results = query.getResultList();

        if (results.size() == 0) {
            return null;
        }

        return results.get(0);
    }
}
