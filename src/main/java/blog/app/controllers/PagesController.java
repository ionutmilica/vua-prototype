package blog.app.controllers;

import vua.http.JsonResponse;
import vua.http.Response;
import vua.routing.Controller;
import vua.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PagesController extends Controller {

    public Response about() {
        return new View("views/blog/about.html");
    }

    public Response contact() {
        Map<String, Object> contact = new HashMap<>();
        contact.put("name", "Ionut Milica");
        contact.put("age", 22);
        contact.put("email", "ionut.milica@gmail.com");

        return new JsonResponse(contact);
    }
}
