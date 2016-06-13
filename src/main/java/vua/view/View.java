package vua.view;

import org.rythmengine.RythmEngine;
import vua.http.Response;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
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

    protected void writeContent(PrintWriter writer) throws IOException {
        RythmEngine engine = getInjector().getInstance(RythmEngine.class);

        ClassLoader loader = this.getClass().getClassLoader();

        URL resource = loader.getResource(file);
        File f = new File(resource.getPath());

        if (data == null) {
            engine.render(writer, f);
        } else {
            engine.render(writer, f, data);
        }
    }
}
