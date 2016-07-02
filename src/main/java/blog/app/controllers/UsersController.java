package blog.app.controllers;

import vua.http.Response;
import vua.http.TextResponse;
import vua.routing.Controller;
import vua.view.View;

import java.util.HashMap;

public class UsersController extends Controller {

    public Response create() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("Title", "My page");
        data.put("Content", "<h1>Hello world!</h1>");

        return new View("views/create.html", data);
    }

    public Response store() {
        //System.out.println("Create user:" + request.method());

        return new TextResponse();
    }
}
