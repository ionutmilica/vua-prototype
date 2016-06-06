package demo.app.controllers;

import vua.http.Response;
import vua.routing.Controller;

public class HomeController extends Controller {

    public Response home() {
        return new Response("Hello world!");
    }

    public Response doHome() {
        return new Response("Post response");
    }
}
