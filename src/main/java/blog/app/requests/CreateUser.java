package blog.app.requests;

import vua.validation.annotations.Required;

public class CreateUser {

    @Required
    private String username;

    @Required
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
