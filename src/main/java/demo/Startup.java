package demo;

import vua.foundation.StartApp;
import vua.server.JettyServer;

public class Startup extends StartApp {

    public static void main(String[] args) throws Exception {
        JettyServer server = new JettyServer();
        server.with(new Startup());
        server.start();
    }
}
