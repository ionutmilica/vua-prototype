package blog;

import blog.app.controllers.HomeController;
import blog.app.controllers.PostsController;
import blog.app.controllers.UsersController;
import vua.contracts.AppRoutes;
import vua.routing.Router;

public class Routes implements AppRoutes {

    public void init(Router router) {
        router.get("/home", HomeController.class, "home");

        router.group("users", r -> {
            r.get("create", UsersController.class, "create");
            r.post("/", UsersController.class, "store");
        });

        router.get("/", PostsController.class, "all");
        router.get("post/{id}/{title?}", PostsController.class, "post").Name("post");
    }
}
