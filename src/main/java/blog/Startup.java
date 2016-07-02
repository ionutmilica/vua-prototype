package blog;

import vua.server.JettyServer;

public class Startup {

    public static void main(String[] args) throws Exception {
        JettyServer server = new JettyServer();
        server.withClass(Startup.class);
        server.mapDirectory("assets/", "/assets");
        server.start();
    }
}
