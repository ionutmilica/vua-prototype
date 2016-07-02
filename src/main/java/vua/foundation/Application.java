package vua.foundation;

import com.google.inject.Inject;
import vua.http.Context;
import vua.http.Renderable;
import vua.http.Response;
import vua.routing.Route;
import vua.routing.Router;
import vua.services.Service;
import vua.view.View;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class Application {

    protected Router router;

    @Inject
    protected Service service;

    @Inject
    public Application(Router router) {
        this.router = router;
    }

    public Router getRouter() {
        return router;
    }

    public void onStart() {
        service.start();
    }

    public void onShutdown() {
        service.stop();
    }

    public void onReceivedRequest(Context context) {
        String method = context.getRequest().method();
        String pathInfo = context.getRequest().pathInfo();
        Response response = null;

        Route route = router.getRoute(method, pathInfo);

        if (route == null) {
            response = new View("views/errors/404.html");
            render(context, response);
            return;
        }

        try {
            response = route.getFilterChain().next(context);
            render(context, response);
        } catch (Exception e) {
            PrintWriter writer =  context.getWriter();
            e.printStackTrace(writer);
        }

    }

    private void render(Context context, Response response) {
        try {
            if (response != null) {
                context.render(response);
            }
        } catch (Exception e) {
            PrintWriter writer =  context.getWriter();
            e.printStackTrace(writer);
        }
    }
}
