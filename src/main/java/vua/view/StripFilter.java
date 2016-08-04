package vua.view;

import com.mitchellbosecke.pebble.extension.Filter;

import java.util.List;
import java.util.Map;

public class StripFilter implements Filter {
    @Override
    public Object apply(Object input, Map<String, Object> args) {
        if (input == null) {
            return null;
        }

        String value = (String) input;

        if (value.length() == 0) {
            return value;
        }

        return value.replaceAll("\\<.*?>","");
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }
}
