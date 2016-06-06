package demo.app.controllers;

import vua.http.JsonResponse;
import vua.http.Redirect;
import vua.http.Response;
import vua.routing.Controller;

class User {
    private int age;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}

public class PagesController extends Controller {

    public Response about() {
        return new Response("About!");
    }

    public Response contact() {
        User user = new User();
        user.setAge(22);
        user.setUsername("ionutmilica");

        return new JsonResponse(user);
    }
}
