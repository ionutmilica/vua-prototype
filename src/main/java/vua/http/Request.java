package vua.http;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Set;
import java.util.TreeSet;

/**
 * Wrapper class over HttpServletRequest
 * I've added some new helper methods
 */
public class Request {
    private String method;
    private HttpServletRequest servletRequest;
    private byte[] bodyBytes;
    private Set<String> headers;

    // Sessions
    private boolean validSession = false;
    private Session session;

    public Request() {
    }

    /**
     * Get request body as a stream.
     *
     * @return InputStream of the request body
     */
    public InputStream bodyStream() {
        try {
            return servletRequest.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get request body as bytes
     *
     * @return Byte[]
     */
    public byte[] bodyAsBytes() {
        if (bodyBytes != null) {
            return bodyBytes;
        }
        try {
            bodyBytes = IOUtils.toByteArray(servletRequest.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bodyBytes;
    }

    /**
     * Get request body as String
     *
     * @return String
     */
    public String body() {
        return new String(bodyAsBytes());
    }

    /**
     * Get the http protocol in protocol/majorVersion.minorVersion format
     *
     * @return A String with http protocol used to make the request
     */
    public String protocol() {
        return servletRequest.getProtocol();
    }

    /**
     * Returns the name of the HTTP method with which this
     * request was made, for example, GET, POST, or PUT.
     * If queryParams("_method") exists, it will be overwritten
     */
    public String method() {
        //
        return servletRequest.getMethod();
    }

    /**
     * Returns the name of the scheme used to make the request
     * E.g: https, http, ftp
     *
     * @return A string with the name of the scheme
     */
    public String scheme() {
        return servletRequest.getScheme();
    }

    /**
     * Returns the MIME type of the body of the request, or
     * <code>null</code> if the type is not known. For HTTP servlets,
     * same as the value of the CGI variable CONTENT_TYPE.
     *
     * @return a <code>String</code> containing the name of the MIME type
     * of the request, or null if the type is not known
     */
    public String contentType() {
        return servletRequest.getContentType();
    }

    /**
     * Returns the length, in bytes, of the request body and made available by
     * the input stream, or -1 if the length is not known ir is greater than
     * Integer.MAX_VALUE. For HTTP servlets,
     * same as the value of the CGI variable CONTENT_LENGTH.
     *
     * @return an integer containing the length of the request body or -1 if
     * the length is not known or is greater than Integer.MAX_VALUE.
     */
    public int contentLength() {
        return servletRequest.getContentLength();
    }

    /**
     * Returns the Internet Protocol (IP) address of the client
     * or last proxy that sent the request.
     */
    public String ip() {
        return servletRequest.getRemoteAddr();
    }

    /**
     * Returns a reconstructed URL of the client used to make the request.
     * The returned URL contains a protocol, server name, port
     * number, and server path, but it does not include query
     * string parameters.
     */
    public String url() {
        return servletRequest.getRequestURL().toString();
    }

    /**
     * Returns the part of this request's URL from the protocol
     * name up to the query string in the first line of the HTTP request.
     * The web container does not decode this String.
     */
    public String uri() {
        return servletRequest.getRequestURI();
    }

    /**
     * Returns the portion of the request URI that indicates the context
     * of the request. The context path always comes first in a request
     * URI. The path starts with a "/" character but does not end with a "/"
     * character. For servlets in the default (root) context, this method
     * returns "". The container does not decode this string.
     *
     * @return String
     */
    public String contextPath() {
        return servletRequest.getContextPath();
    }

    /**
     * Returns any extra path information associated with
     * the URL the client sent when it made this request.
     * The extra path information follows the servlet path
     * but precedes the query string and will start with
     * a "/" character.
     *
     * @return A string with the path info
     */
    public String pathInfo() {
        return servletRequest.getPathInfo();
    }

    /**
     * Get the server port
     *
     * @return An int containing the server port used to accept new client connections
     */
    public int port() {
        return servletRequest.getServerPort();
    }

    /**
     * Get client user agent name
     *
     * @return A string having the client user agent name
     */
    public String userAgent() {
        return servletRequest.getHeader("User-Agent");
    }

    /**
     * Get header value by name
     *
     * @param header name used to find the header
     * @return String containing the header value
     */
    public String headers(String header) {
        return servletRequest.getHeader(header);
    }

    /**
     * Get the pairs of name, value for every header sent by the client request
     *
     * @return all headers
     */
    public Set<String> headers() {
        if (headers == null) {
            headers = new TreeSet<>();
            Enumeration<String> enumeration = servletRequest.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                headers.add(enumeration.nextElement());
            }
        }
        return headers;
    }

    public Set<String> queryParams() {
        return servletRequest.getParameterMap().keySet();
    }

    /**
     * Get query parameter map
     *
     * @return
     */
    public QueryMap queryMap() {
        return new QueryMap(servletRequest.getParameterMap());
    }


    /**
     * Get the current session attached to the request or create one if there is none attached to the request
     *
     * @return a session object
     */
    public Session session() {
        if (session == null || !validSession) {
            validSession = true;
            setValidSession(false);
            session = new Session(servletRequest.getSession(), this);
        }
        return session;
    }

    /**
     * Get the current session attached to the request or create one if the <code>create</code> is set to true
     * and there is no session attached to the request
     *
     * @return a session object
     */
    public Session session(boolean create) {
        if (session == null || !validSession) {
            HttpSession httpSession = servletRequest.getSession(create);
            if (httpSession != null) {
                setValidSession(true);
                session = new Session(httpSession, this);
            } else {
                session = null;
            }
        }
        return session;
    }

    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    /**
     * Get original request object.
     *
     * @return A HttpServletRequest object
     */
    public HttpServletRequest raw() {
        return servletRequest;
    }

    /**
     * Set the session validity
     *
     * @param validSession the session validity
     */
    void setValidSession(boolean validSession) {
        this.validSession = validSession;
    }
}
