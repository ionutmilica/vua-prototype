package vua.view;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import vua.http.BaseResponse;
import vua.http.Context;
import vua.http.Renderable;
import java.util.HashMap;
import java.util.Map;

public class View extends BaseResponse {
    private String file;
    private Map<String, Object> data;

    public View(String file) {
        this.file = file;
        withContentType("text/html");
    }

    public View(String file, Map<String, Object> data) {
        this(file);
        this.data = data;
    }

    public View with(String key, Object val) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, val);
        return this;
    }

    public Renderable getRenderable() {
        return new Renderable() {
            @Override
            public void render(Context context) throws Exception {
                PebbleEngine engine = new PebbleEngine.Builder().build();
                PebbleTemplate compiledTemplate = engine.getTemplate(file);
                compiledTemplate.evaluate(context.getWriter(), data);
            }
        };
    }
}
