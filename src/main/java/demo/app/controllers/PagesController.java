package demo.app.controllers;

import vua.http.Response;
import vua.routing.Controller;

public class PagesController extends Controller {

    public Response about() {
        return new Response("About!");
    }

    public Response contact() {
        return new Response("Contact!");
    }
}
