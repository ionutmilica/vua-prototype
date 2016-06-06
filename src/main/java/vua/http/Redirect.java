package vua.http;

import javax.servlet.http.HttpServletResponse;

public class Redirect extends Response {

    public static Redirect to(String url) {
        Redirect response = new Redirect();
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.addHeader("Location", url);

        return response;
    }
}
