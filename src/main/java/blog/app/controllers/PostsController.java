package blog.app.controllers;

import blog.app.dao.PostDao;
import blog.app.models.Post;
import com.google.inject.Inject;
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

    public Response post(Request request) {
        return new View("views/blog/post.html");
    }
}
