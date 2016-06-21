package vua.routing;

import java.lang.reflect.Method;

public class Action {
    private Class controller;
    private Method method;

    public Action(Class controllerClass, String action) {
        this.controller = controllerClass;
        this.method = actionToMethod(controllerClass, action);
    }

    private Method actionToMethod(Class controllerClass, String action) {
        Method tmp = null;

        for (Method method : controllerClass.getMethods()) {
            if (method.getName().equals(action)) {
                tmp = method;
            }
        }

        return tmp;
    }

    public Class<?> getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }
}
