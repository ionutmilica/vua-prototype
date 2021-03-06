package vua.http;

import java.util.Map;

public interface Response {

    /**
     * Get the content type
     *
     * @return String
     */
    String getContentType();

    /**
     * Set the content type for the response
     *
     * @param contentType Content type
     */
    void withContentType(String contentType);

    /**
     * Add a header into the response
     *
     * @param name Name of the header
     * @param value Value of the header
     * @return Response object
     */
    Response withHeader(String name, String value);

    /**
     * Get header value by its key
     *
     * @param name Name of the header
     * @return value of the header
     */
    String getHeader(String name);

    /**
     * Get the stored headers that will be sent to the browser when the response will be rendered
     *
     * @return A map of headers
     */
     Map<String, String> getHeaders();

    /**
     * Set the response code
     *
     * @param status Http status code
     * @return Response object
     */
    Response withStatus(int status);

    /**
     * Get the http status code used for the response
     *
     * @return A integer
     */
    int getStatus();

    /**
     * Get the renderable object that should be rendered with the response
     *
     * @return Renderable object
     */
    Renderable getRenderable();
}
