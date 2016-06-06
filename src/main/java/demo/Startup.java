package demo;

import vua.server.JettyServer;

public class Startup {

    public static void main(String[] args) throws Exception {
        JettyServer server = new JettyServer();
        server.setAppPackage(Startup.class.getPackage());
        server.start();
    }
}
