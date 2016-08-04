package blog;

import blog.app.controllers.PagesController;
import blog.app.controllers.PostsController;
import vua.contracts.AppRoutes;
import vua.routing.Router;

public class Routes implements AppRoutes {

    public void init(Router router) {
        router.get("/", PostsController.class, "all").name("home");
        router.get("post/{id}/{title?}", PostsController.class, "post").name("post");

        router.get("about", PagesController.class, "about").name("about");
        router.get("contact", PagesController.class, "contact").name("contact");

        router.get("api/post/{id}", PostsController.class, "postJson");
    }
}
