package vua.servlet;

import com.google.inject.Inject;
import com.google.inject.Injector;
import vua.foundation.Application;
import vua.routing.Router;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDispatcher extends HttpServlet {

    @Inject
    private Injector injector;

    public ServletDispatcher() { }

    public ServletDispatcher(Injector injector) {
        this.injector = injector;
    }

    public void service(ServletRequest req, ServletResponse resp) {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        ServletContext context = getServletContext();

        //
        // Fire the framework
        Application app = injector.getInstance(Application.class);

        app.onReceivedRequest(context, request, response);
    }
}
