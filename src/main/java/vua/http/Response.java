package vua.http;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private String content;
    private int status = 200;
    private Map<String, String> headers = new HashMap<>();

    public Response() {}

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

    public void render(HttpServletResponse response) throws IOException {
        response.setStatus(status);

        // send the headers
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            response.setHeader(entry.getKey(), entry.getValue());
        }

        // Write the content to the client
        PrintWriter writer = response.getWriter();
        writer.print(content);
    }
}
