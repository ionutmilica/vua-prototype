package vua.http;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Context {

    private final HttpServletResponse response;
    private final ServletContext context;
    private final HttpServletRequest request;

    public Context(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.context = context;
    }

    public String getMethod() {
        return request.getMethod();
    }

    public String getPathInfo() {
        return request.getPathInfo();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
