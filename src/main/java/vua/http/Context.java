package vua.http;

import com.google.inject.Inject;
import com.google.inject.Injector;
import vua.routing.Router;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Context {

    private HttpServletResponse response;
    private ServletContext context;
    private HttpServletRequest request;

    private Request wrappedRequest = null;
    private Injector injector;
    private HashMap<String, Object> storage;
    private Router router;

    @Inject
    public Context(Injector injector) {
        this.injector = injector;
    }

    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.context = context;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Router getRouter() {
        return router;
    }

    public void setParameters(Map<String, String> parameters) {
        Request request = getRequest();
        request.setParameters(parameters);
    }

    public void put(String key, Object value) {
        storage.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) storage.get(key);
    }

    public boolean has(String key) {
        return storage.containsKey(key);
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

    public Injector getInjector() {
        return injector;
    }

    public void render(Response response) throws Exception {
        Renderable renderable = response.getRenderable();

        this.response.setStatus(response.getStatus());
        this.response.setContentType(response.getContentType());

        for (Map.Entry<String, String> entry : response.getHeaders().entrySet()) {
            this.response.setHeader(entry.getKey(), entry.getValue());
        }

        renderable.render(this);
    }

    public HttpServletRequest getServletRequest() {
        return request;
    }

    public HttpServletResponse getServletResponse() {
        return response;
    }

}
