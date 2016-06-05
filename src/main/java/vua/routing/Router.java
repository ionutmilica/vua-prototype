package vua.routing;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class Router {

    protected HashMap<String, Tree> methods;

    public Router() {
        methods = new HashMap<>();
        init();
    }

    private void init() {
        String[] m = new String[] {"GET", "POST", "PUT", "DELETE"};

        for (String method : m) {
            methods.put(method, new Tree());
        }
    }

    public void handle(ServletContext context, HttpServletRequest request, HttpServletResponse response) {

        if ( ! methods.containsKey(request.getMethod())) {
            try {
                response.getWriter().printf("Method not found!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        System.out.printf("%s %s\n", request.getMethod(), request.getPathInfo());
        System.out.println("IT works!");
    }
}
