package demo;

import demo.app.controllers.HomeController;
import demo.app.controllers.PagesController;
import vua.contracts.AppRoutes;
import vua.routing.Router;

public class Routes implements AppRoutes {

    public void init(Router router) {

        router.group("pages", r -> {
            r.get("about", PagesController.class, "about");
            r.get("contact", PagesController.class, "contact");
        });

        router.get("/home", HomeController.class, "home");
        router.post("/home", HomeController.class, "doHome");

    }
}
