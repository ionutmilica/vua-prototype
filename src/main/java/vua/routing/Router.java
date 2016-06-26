package vua.routing;

import com.google.inject.Inject;
import com.google.inject.Injector;
import vua.contracts.routing.Filter;
import vua.contracts.routing.FilterChain;
import vua.http.Context;
import vua.http.Response;
import vua.params.MethodInvoker;
import vua.routing.filters.FilterChainEnd;
import vua.routing.filters.FilterChainImpl;
import vua.utils.StringUtil;
import vua.view.View;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.*;

public class Router {

    private Injector injector;
    private HashMap<String, Tree> methods = new HashMap<>();
    private ArrayList<Group> groups = new ArrayList<>();
    private ArrayList<Object> middleware = new ArrayList<>();

    @Inject
    public Router(Injector injector) {
        this.injector = injector;
        init();
    }

    private void init() {
        String[] m = new String[]{"GET", "POST", "PUT", "PATCH", "DELETE"};

        for (String method : m) {
            methods.put(method, new Tree());
        }
    }

    public Route get(String path, Class controllerClass, String action) {
        Action a = new Action(controllerClass, action);
        return insert("GET", path, a);
    }

    public Route post(String path, Class controllerClass, String action) {
        Action a = new Action(controllerClass, action);
        return insert("POST", path, a);
    }

    public Route put(String path, Class controllerClass, String action) {
        Action a = new Action(controllerClass, action);
        return insert("PUT", path, a);
    }

    public Route patch(String path, Class controllerClass, String action) {
        Action a = new Action(controllerClass, action);
        return insert("PATCH", path, a);
    }

    public Route delete(String path, Class controllerClass, String action) {
        Action a = new Action(controllerClass, action);
        return insert("DELETE", path, a);
    }

    /**
     * General method for route creation
     *
     * @param method
     * @param path
     * @param action
     * @return
     */
    private Route insert(String method, String path, Action action) {
        Tree tree = methods.get(method);

        StringBuilder builder = new StringBuilder();

        path = StringUtil.trim(path, '/');

        if (groups.size() > 0) {
            for (Group group : groups) {
                group.buildPrefix(builder);
            }
        }

        if (!path.isEmpty()) {
            // /home -> home
            if (builder.length() > 0) {
                builder.append('/');
            }
            builder.append(path);
        }

        LinkedList<Class<? extends Filter>> filters = new LinkedList<>();

        Class controller = action.getController();
        Method ctrlMethod = action.getMethod();

        if (controller == null || ctrlMethod == null) {
            throw new RuntimeException("Cannot accept a null controller or null action!");
        }

        filters.addAll(getClassFilters(action.getController()));
        FilterWith filterWith = action.getMethod().getAnnotation(FilterWith.class);

        if (filterWith != null) {
            filters.addAll(Arrays.asList(filterWith.value()));
        }

        Route route = new Route(this, buildFilterChain(injector, filters, controller, ctrlMethod, null));
        Node node = tree.insert(builder.toString(), route);
        route.setNode(node);

        return route;
    }

    public Route getRoute(String method, String path) {
        if (!methods.containsKey(method)) {
            return null;
        }

        Tree tree = methods.get(method);
        NodeMatchResult result = tree.match(path);

        if ( ! result.isMatched()) {
            return null;
        }


        return (Route) result.getNode().getHandler();
    }

    private FilterChain buildFilterChain(Injector injector,
                                         LinkedList<Class<? extends Filter>> filters,
                                         Class<?> controller,
                                         Method controllerMethod,
                                         Response response) {
        if (filters.isEmpty()) {
            if (response == null) {
                return new FilterChainEnd(injector.getProvider(controller), MethodInvoker.build(controllerMethod, injector));
            } else {
                return new FilterChainEnd(response);
            }
        } else {
            Class<? extends Filter> filter = filters.pop();
            FilterChain chain = buildFilterChain(injector, filters, controller, controllerMethod, response);
            return new FilterChainImpl(injector.getProvider(filter), chain);
        }
    }

    private Set<Class<? extends Filter>> getClassFilters(Class controller) {
        LinkedHashSet<Class<? extends Filter>> filters = new LinkedHashSet<>();
        if (controller.getSuperclass() != null) {
            filters.addAll(getClassFilters(controller.getSuperclass()));
        }
        if (controller.getInterfaces() != null) {
            for (Class cls : controller.getInterfaces()) {
                filters.addAll(getClassFilters(cls));
            }
        }

        FilterWith filterWith = (FilterWith) controller.getAnnotation(FilterWith.class);
        if (filterWith != null) {
            filters.addAll(Arrays.asList(filterWith.value()));
        }
        // And return
        return filters;
    }

    /**
     * Group routes by their prefix
     *
     * @param prefix
     * @param group
     */
    public void group(String prefix, RouteGroup group) {
        groups.add(new Group(prefix));
        group.init(this);
        groups.remove(groups.size() - 1);
    }

}
