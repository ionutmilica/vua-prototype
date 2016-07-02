package blog.app.dao;

import blog.app.models.Post;
import blog.app.models.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Random;

public class SetupDao {

    @Inject
    private Provider<EntityManager> entityManagerProvider;

    private Random random = new Random();

    @Transactional
    public void setup() {
        System.out.println("Hello");
        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM Post x");
        List posts = q.getResultList();

        if (posts.size() == 0) {
            User user = new User("ionut", "dev");
            entityManager.persist(user);

            int numOfPosts = random.nextInt(9) + 1;

            for (int i = 1; i < numOfPosts + 1; i++) {
                Post post = new Post(String.format("Articol %d", i), "Continut articol");
                post.setUser(user);
                entityManager.persist(post);
            }
        }
    }
}
