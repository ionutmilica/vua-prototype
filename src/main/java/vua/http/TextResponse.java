package vua.http;

import java.io.IOException;
import java.io.Writer;

public class TextResponse extends BaseResponse {
    private String content = "";

    public TextResponse() {
    }

    public TextResponse(String content) {
        this.content = content;
    }

    public TextResponse(String content, int status) {
        this(content);
        withStatus(status);
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
