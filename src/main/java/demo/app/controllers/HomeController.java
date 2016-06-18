package demo.app.controllers;

import vua.http.QueryMap;
import vua.http.Request;
import vua.http.Response;
import vua.routing.Controller;

public class HomeController extends Controller {

    public Response home(Request request) {
        QueryMap params = request.queryMap();
        return new Response(params.get("user").value());
    }

    public Response doHome(Request request) {
        return new Response(request.method());
    }
}
