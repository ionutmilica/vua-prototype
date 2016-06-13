package demo.app.requests;

import vua.validation.annotations.Required;
import vua.validation.FormRequest;

public class CreateUser extends FormRequest {

    @Required
    public String username;

    @Required
    public String password;
}
