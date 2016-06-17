package vua.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonResponse extends Response {
    private Object object;

    public JsonResponse(Object object) {
        this.object = object;
    }

    public JsonResponse(Object object, int status) {
        this(object);
        this.status = status;
    }

    @Override
    public void render(HttpServletResponse response) throws Exception {
        response.setContentType("application/json");
        super.render(response);
    }

    protected void writeContent(PrintWriter writer) throws Exception {
        ObjectMapper objectMapper = getInjector().getInstance(ObjectMapper.class);
        objectMapper.writeValue(writer, object);
    }
}
