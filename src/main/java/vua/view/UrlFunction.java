package vua.view;

import com.mitchellbosecke.pebble.extension.Function;
import vua.routing.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UrlFunction implements Function {

    private Router router;

    public UrlFunction(Router router) {
        this.router = router;
    }

    @Override
    public Object execute(Map<String, Object> args) {

        int i = 0;

        String routerName = "";
        ArrayList<String> params = new ArrayList<>();

        while (args.containsKey(String.valueOf(i))) {
            Object parameter = args.get(String.valueOf(i));

            if (i == 0) {
                routerName = (String) parameter;
            } else {
                params.add(parameter.toString());
            }
            i++;
        }

        return router.urlFor(routerName, params.toArray(new String[0]));
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }
}
