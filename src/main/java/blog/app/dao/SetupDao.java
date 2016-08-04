package blog.app.dao;

import blog.app.models.Post;
import blog.app.models.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SetupDao {

    @Inject
    private Provider<EntityManager> entityManagerProvider;

    private Random random = new Random();

    @Transactional
    public void setup() {
        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM Post x");
        List posts = q.getResultList();

        if (posts.size() == 0) {
            User user = new User("ionut", "dev");
            entityManager.persist(user);

            int numOfPosts = randomBetween(1, 10);

            for (int i = 1; i < numOfPosts + 1; i++) {
                Lorem lorem = LoremIpsum.getInstance();
                Post post = new Post();
                post.setTitle(lorem.getTitle(5, 10));
                post.setContent(lorem.getHtmlParagraphs(4, 8));
                post.setBackground(String.format("%d.jpg", randomBetween(1, 10)));
                post.setUser(user);

                // Save the user
                entityManager.persist(post);
            }
        }
    }

    private int randomBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
