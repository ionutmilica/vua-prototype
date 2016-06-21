package vua.http;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private String content = "";
    private int status = 200;
    private Map<String, String> headers = new HashMap<>();
    private String contentType = "text/plain";

    public Response() {
    }

    public Response(String content) {
        this.content = content;
    }

    public Response(String content, int status) {
        this(content);
        withStatus(status);
    }

    /**
     * Get the content type
     *
     * @return String
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Set the content type for the response
     *
     * @param contentType Content type
     */
    public void withContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Add a header into the response
     *
     * @param name Name of the header
     * @param value Value of the header
     * @return Response object
     */
    public Response withHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    /**
     * Get header value by its key
     *
     * @param name Name of the header
     * @return value of the header
     */
    public String getHeader(String name) {
        return headers.get(name);
    }

    /**
     * Get the stored headers that will be sent to the browser when the response will be rendered
     *
     * @return A map of headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Set the response code
     *
     * @param status Http status code
     * @return Response object
     */
    public Response withStatus(int status) {
        this.status = status;

        return this;
    }

    /**
     * Get the http status code used for the response
     *
     * @return A integer
     */
    public int getStatus() {
        return status;
    }

    /**
     * Get the renderable object that should be rendered with the response
     *
     * @return Renderable object
     */
    public Renderable getRenderable() {
        return new Renderable() {
            @Override
            public void render(Context context) throws IOException {
                Writer writer = context.getWriter();
                writer.write(content);
            }
        };
    }
}
