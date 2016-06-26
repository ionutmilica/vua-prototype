package vua.http;

import javax.servlet.http.HttpServletResponse;

public class Redirect extends BaseResponse {

    public static Redirect to(String url) {
        Redirect response = new Redirect();
        response.withStatus(HttpServletResponse.SC_SEE_OTHER);
        response.withHeader("Location", url);

        return response;
    }

    @Override
    public Renderable getRenderable() {
        return new Renderable() {
            @Override
            public void render(Context context) throws Exception {
                // redirect don't deal with content
            }
        };
    }
}
