package vua.http;

import com.google.inject.Injector;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private Injector injector;
    protected String content = "";
    protected int status = 200;
    protected Map<String, String> headers = new HashMap<>();

    public Response() {
        this.content = "";
    }

    public Response(String content) {
        this.content = content;
    }

    public Response addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void render(HttpServletResponse response) throws Exception {
        response.setStatus(status);
        sendHeaders(response);
        writeContent(response.getWriter());
    }

    protected void writeContent(PrintWriter writer) throws Exception {
        writer.write(content);
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    public Injector getInjector() {
        return injector;
    }

    /**
     *
     *
     * @param response
     */
    protected void sendHeaders(HttpServletResponse response) {
        // send the headers
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            response.setHeader(entry.getKey(), entry.getValue());
        }
    }
}
