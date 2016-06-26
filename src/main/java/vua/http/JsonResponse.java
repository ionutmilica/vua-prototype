package vua.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Writer;

public class JsonResponse extends BaseResponse {
    private Object object;
    private String callback = null;

    public JsonResponse(Object object) {
        this.object = object;
    }

    public JsonResponse(Object object, int status) {
        this(object);
        withStatus(status);
    }

    public Response withCallback(String name) {
        this.callback = name;
        return this;
    }

    @Override
    public String getContentType() {
        return callback == null ? "application/json" : "application/javascript";
    }

    public Renderable getRenderable() {
        return new Renderable() {
            @Override
            public void render(Context context) throws IOException {
                Writer writer = context.getWriter();
                ObjectMapper objectMapper = context.getInjector().getInstance(ObjectMapper.class);
                if (callback == null) {
                    objectMapper.writeValue(writer, object);
                } else {
                    String value = objectMapper.writeValueAsString(object);
                    writer.write(String.format("%s(%s);", callback, value));
                }
            }
        };
    }
}
