package vua.http;

import com.google.inject.Inject;
import com.google.inject.Injector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Context {

    private HttpServletResponse response;
    private ServletContext context;
    private HttpServletRequest request;

    private Request wrappedRequest = null;
    private Injector injector;

    @Inject
    public Context(Injector injector) {
        this.injector = injector;
    }

    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.context = context;
    }

    /**
     * Get wrapped request
     *
     * @return
     */
    public Request getRequest() {
        if (wrappedRequest == null) {
            wrappedRequest = new Request();
            wrappedRequest.setServletRequest(request);
        }
        return wrappedRequest;
    }

    public PrintWriter getWriter() {
        try {
            return response.getWriter();
        } catch (IOException e) {
            //
        }
        return null;
    }

    public HttpServletRequest getServletRequest() {
        return request;
    }

    public void renderResponse(Response response) throws Exception {
        response.setInjector(injector);
        response.render(getResponse());
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
