package vua.http;

public class Redirect extends Response {

    public Redirect(String content) {
        super(content);
    }

    public Redirect to(String url) {
        return this;
    }
}
