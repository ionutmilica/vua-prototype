package demo.app.controllers;

import vua.http.Response;
import vua.routing.Controller;

public class HomeController extends Controller {

    public Response index() {
        return new Response("Hello world!");
    }
}
