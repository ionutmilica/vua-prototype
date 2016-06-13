package vua.http;

import javax.servlet.http.HttpServletRequest;

public class Request {

    private String method;
    private HttpServletRequest request;

    public Request() {

    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getOriginalRequest() {
        return request;
    }

    public String getMethod() {
        return request.getMethod();
    }
}
