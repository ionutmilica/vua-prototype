package blog.app.controllers;

import blog.app.dao.PostDao;
import blog.app.models.Post;
import com.google.inject.Inject;
import vua.http.JsonResponse;
import vua.http.Redirect;
import vua.routing.Controller;
import vua.http.Request;
import vua.http.Response;
import vua.view.View;
import java.util.List;

public class PostsController extends Controller {

    @Inject
    private PostDao postDao;

    public Response all() {
        List<Post> posts = postDao.getPosts();

        return new View("views/blog/posts.html").with("posts", posts);
    }

    public Response postJson(Request request) {
        long id = (Long) request.parameter("id", Long.class);

        return new JsonResponse(postDao.postById(id));
    }

    public Response post(Request request) {
        long id = (Long) request.parameter("id", Long.class);

        Post post = postDao.postById(id);

        if (post == null) {
            return Redirect.to("/");
        }

        return new View("views/blog/post.html").with("post", post);
    }
}
