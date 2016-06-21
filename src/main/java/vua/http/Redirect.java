package vua.http;

import javax.servlet.http.HttpServletResponse;

public class Redirect extends Response {

    public static Redirect to(String url) {
        Redirect response = new Redirect();
        response.withStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.withHeader("Location", url);

        return response;
    }
}
