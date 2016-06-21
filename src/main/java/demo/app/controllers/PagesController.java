package demo.app.controllers;

import demo.app.models.User;
import vua.http.JsonResponse;
import vua.http.Request;
import vua.http.Response;
import vua.routing.Controller;

public class PagesController extends Controller {

    public Response about() {
        return new Response("About!");
    }

    public Response contact(Request request) {
        User user = new User();
        user.setAge(22);
        user.setUsername("ionutmilica");

        return new JsonResponse(user);
    }
}
