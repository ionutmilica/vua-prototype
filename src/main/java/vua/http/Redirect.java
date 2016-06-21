package vua.http;

import javax.servlet.http.HttpServletResponse;

public class Redirect extends Response {

    public static Redirect to(String url) {
        Redirect response = new Redirect();
        response.withStatus(HttpServletResponse.SC_SEE_OTHER);
        response.withHeader("Location", url);

        return response;
    }
}
