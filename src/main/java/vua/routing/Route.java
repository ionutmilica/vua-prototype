package vua.routing;

import vua.contracts.routing.FilterChain;

public class Route {

    private Node node;
    private Router router;
    private FilterChain filterChain;
    private String name;

    public Route(Router router, FilterChain chain) {
        this.router = router;
        this.filterChain = chain;
    }

    public FilterChain getFilterChain() {
        return filterChain;
    }

    public Route setNode(Node node) {
        this.node = node;
        return this;
    }

    public Route Name(String name) {
        this.name = name;
        // set route name
        return this;
    }
}
