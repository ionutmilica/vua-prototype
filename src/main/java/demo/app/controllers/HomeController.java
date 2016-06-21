package demo.app.controllers;

import vua.http.Request;
import vua.http.Response;
import vua.routing.Controller;

public class HomeController extends Controller {
    public Response home(Request request) {
        System.out.println(request.url());
        System.out.println(request.uri());
        System.out.println(request.pathInfo());
        System.out.println(request.userAgent());
        System.out.println(request.ip());
        System.out.println(request.contentType());
        System.out.println(request.body());

        return new Response("Hello world!");
    }
}
