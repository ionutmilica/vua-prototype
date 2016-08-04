package vua.routing;

import java.util.Map;

public class RouteWithData {
    private final Route route;
    private final Map<String, String> data;

    public RouteWithData(Route route, Map<String, String> data) {
        this.route = route;
        this.data = data;
    }

    public Route getRoute() {
        return route;
    }

    public Map<String, String> getData() {
        return data;
    }
}
