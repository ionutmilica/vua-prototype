package vua.view;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.Function;
import vua.http.Context;

import java.util.HashMap;
import java.util.Map;

public class VuaExtension extends AbstractExtension {

    private Context context;

    public VuaExtension(Context context) {
        this.context = context;
    }

    public Map<String, Filter> getFilters() {
        Map<String, Filter> filterMap = new HashMap<>();

        filterMap.put("strip", new StripFilter());

        return filterMap;
    }

    public Map<String, Function> getFunctions() {
        Map<String, Function> functionMap = new HashMap<>();

        functionMap.put("url", new UrlFunction(context.getRouter()));

        return functionMap;

    }
}
