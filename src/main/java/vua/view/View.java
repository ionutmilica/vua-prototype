package vua.view;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import vua.http.Response;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class View extends Response {

    private String file;
    private Map<String, Object> data;

    public View(String file) {
        this.file = file;
    }

    public View(String file, Map<String, Object> data) {
        this(file);
        this.data = data;
    }

    public void set(String key, Object val) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, val);
    }

    protected void writeContent(PrintWriter writer) throws Exception {
        PebbleEngine engine = new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate = engine.getTemplate(file);
        compiledTemplate.evaluate(writer, data);
    }
}
