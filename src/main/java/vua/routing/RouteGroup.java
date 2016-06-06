package vua.routing;

@FunctionalInterface
public interface RouteGroup {
    void init(Router router);
}