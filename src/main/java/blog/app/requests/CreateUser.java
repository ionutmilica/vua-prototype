package blog.app.requests;

import vua.validation.annotations.Confirmed;
import vua.validation.annotations.Matches;
import vua.validation.annotations.Max;
import vua.validation.annotations.Required;

public class CreateUser {

    @Required
    @Matches(regexp = "[a-bA-z]+")
    private String username;

    @Required
    @Max(30)
    @Confirmed
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
