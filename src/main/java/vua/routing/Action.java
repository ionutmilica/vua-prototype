package vua.routing;

import java.lang.reflect.Method;

public class Action {

    private Class controller;
    private Method method;
    private Controller controllerInstance;

    public Action(Class controllerClass, String action) {
        this.controller = controllerClass;
        this.method = actionToMethod(controllerClass, action);
        try {
            controllerInstance = (Controller) controller.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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

    public Controller getControllerInstance() {
        return controllerInstance;
    }

    public Method getControllerMethod() {
        return method;
    }
}
