package vua.routing;

import java.util.HashMap;

public class MatchResult {
    private HashMap<String, String> data;
    private boolean isMatched;

    public MatchResult() {
        data = new HashMap<>();
        isMatched = false;
    }

    public void setMatched(boolean current) {
        isMatched = current;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void combine(MatchResult matcher) {
        data.putAll(matcher.getResult());
    }

    public HashMap<String, String> getResult() {
        return data;
    }
}

class NodeMatchResult extends MatchResult {

    private Node node;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
