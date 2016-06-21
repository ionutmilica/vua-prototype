package demo;

import demo.app.controllers.HomeController;
import demo.app.controllers.PagesController;
import demo.app.controllers.UsersController;
import vua.contracts.AppRoutes;
import vua.routing.Router;

public class Routes implements AppRoutes {

    public void init(Router router) {


        router.group("pages", r -> {
            r.get("about", PagesController.class, "about");
            r.get("contact", PagesController.class, "contact");
        });

        router.get("/home", HomeController.class, "home");

        router.group("users", r -> {
            r.get("create", UsersController.class, "create");
            r.post("/", UsersController.class, "store");
        });
    }
}
