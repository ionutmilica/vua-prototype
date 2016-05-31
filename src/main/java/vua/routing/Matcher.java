package vua.routing;

import java.util.HashMap;

class Matcher {

    private HashMap<String, String> data;
    private Node node;
    private boolean isMatched;

    public Matcher() {
        data = new HashMap<>();
        isMatched = false;
    }

    public void setMatched(boolean current) {
        isMatched = current;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void combine(Matcher matcher) {
        data.putAll(matcher.getData());
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
