package vua.server;

import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import vua.servlet.ServletListener;

import java.util.ArrayList;

public class JettyServer {

    private int port = 8080;
    private Class startClass;
    private ArrayList<Handler> handlers = new ArrayList<>();

    public JettyServer() {
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void withClass(Class startClass) {
        this.startClass = startClass;
    }

    public void start() throws Exception {
        Server server = new Server(port);

        handlers.add(createContextHandler());

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(handlers.toArray(new Handler[0]));
        server.setHandler(contexts);

        System.out.println(server.dump());

        server.start();
        server.join();
    }

    /**
     * Add a new directory to the file server
     *
     * @param localPath  Path to the resource directory, ex: "WEB-INF/assets/"
     * @param publicPath Public uri ex: "/assets"
     */
    public void mapDirectory(String localPath, String publicPath) {
        ContextHandler context = new ContextHandler();
        context.setContextPath(publicPath);

        ResourceHandler handler = new ResourceHandler();
        handler.setBaseResource(Resource.newClassPathResource(localPath));
        context.setHandler(handler);

        handlers.add(context);
    }

    /**
     * Listen
     *
     * @return
     */
    private ContextHandler createContextHandler() {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.addServlet(DefaultServlet.class, "/");
        contextHandler.addFilter(GuiceFilter.class, "/*", null);
        contextHandler.addEventListener(new ServletListener(startClass));

        return contextHandler;
    }
}
