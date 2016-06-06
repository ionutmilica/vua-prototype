package vua.server;

import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import vua.servlet.ServletListener;

public class JettyServer {

    protected int port = 8080;
    private Package appPk;

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
        contextHandler.addEventListener(new ServletListener(appPk));

        return contextHandler;
    }

    public void setAppPackage(Package pk) {
        this.appPk = pk;
    }
}
