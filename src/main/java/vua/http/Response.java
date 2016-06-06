package vua.http;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Response {

    private String content;
    private int status = 200;

    public Response(String content) {
        this.content = content;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void render(HttpServletResponse response) throws IOException {
        response.setStatus(status);
        
        // Write the content to the client
        PrintWriter writer = response.getWriter();
        writer.print(content);
    }
}
