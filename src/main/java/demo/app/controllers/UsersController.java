package demo.app.controllers;

import demo.app.requests.CreateUser;
import vua.http.Response;
import vua.routing.Controller;
import vua.validation.FormRequest;
import vua.view.View;

import java.util.HashMap;

public class UsersController extends Controller {

    public Response create() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("Title", "My page");
        data.put("Content", "<h1>Hello world!</h1>");

        return new View("views/create.html", data);
    }

    public Response store(CreateUser request) {
        System.out.println("Create user:" + request.getMethod());
        return new Response();
    }
}
