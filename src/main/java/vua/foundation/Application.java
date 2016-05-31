package vua.foundation;

import com.google.inject.Inject;
import vua.routing.Router;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Application {

    @Inject
    protected Router router;

    public Router getRouter() {
        return router;
    }

    public void onStart() {
    }

    public void onShutdown() {

    }

    public void onReceivedRequest(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        router.handle(context, request, response);
    }
}
