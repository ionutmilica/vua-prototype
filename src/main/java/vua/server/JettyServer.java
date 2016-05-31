package vua.server;

import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import vua.foundation.StartApp;
import vua.servlet.ServletListener;

public class JettyServer {

    protected int port = 8080;
    private StartApp startup;

    public JettyServer() { }

    public void setPort(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        Server server = new Server(port);
        server.setHandler(createContextHandler());
        server.start();
        server.join();
    }

    public ContextHandler createContextHandler() {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.addServlet(DefaultServlet.class, "/");
        contextHandler.addFilter(GuiceFilter.class, "/*", null);
        contextHandler.addEventListener(new ServletListener(startup));

        return contextHandler;
    }

    public void with(StartApp startup) {
        this.startup = startup;
    }
}
