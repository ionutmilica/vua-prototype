package demo.app.controllers;

import demo.app.filters.AuthFilter;
import vua.http.Request;
import vua.http.Response;
import vua.routing.Controller;
import vua.routing.FilterWith;

@FilterWith(AuthFilter.class)
public class HomeController extends Controller {

    public Response home(Request request) {
        System.out.println(request.url());
        System.out.println(request.uri());
        System.out.println(request.pathInfo());
        System.out.println(request.userAgent());
        System.out.println(request.ip());
        System.out.println(request.contentType());

        return new Response("Hello world!");
    }
}
