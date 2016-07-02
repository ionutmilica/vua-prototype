package blog.app.controllers;

import blog.app.requests.CreateUser;
import vua.http.Request;
import vua.http.Response;
import vua.http.TextResponse;
import vua.routing.Controller;
import vua.validation.Validator;

public class HomeController extends Controller {

    public Response home(Request request) {
        Validator<CreateUser> validator = new Validator<>(new CreateUser(), request);

        if (validator.passes()) {
            CreateUser createUser = validator.getBean();

            System.out.println(createUser.getUsername());
        }



        return new TextResponse(validator.getErrors().toString());
    }
}
