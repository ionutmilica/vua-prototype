package vua.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Injector;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class JsonResponse extends Response {

    private Injector injector;
    //private ObjectMapper objectMapper;
    private Object object;

    public JsonResponse(Object object) {
        this.object = object;
    }

    public JsonResponse(Object object, int status) {
        this(object);
        this.status = status;
    }

    @Override
    public void render(HttpServletResponse response) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        sendHeaders(response);

        ObjectMapper objectMapper = getInjector().getInstance(ObjectMapper.class);
        objectMapper.writeValue(response.getWriter(), object);
    }
}
