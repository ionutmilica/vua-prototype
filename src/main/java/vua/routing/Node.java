package vua.routing;

import java.util.ArrayList;
import java.util.Optional;

public class Node {

    private String segment;
    private Pattern pattern;
    private Object handler;
    private Node parent = null;

    private ArrayList<Node> children;

    public Node(String pattern, Object handler) {
        this.segment = pattern;
        this.pattern = new Pattern(segment);
        this.handler = handler;
        this.children = new ArrayList<>();
    }

    public Node(String pattern) {
        this.segment = pattern;
        this.pattern = new Pattern(segment);
        this.children = new ArrayList<>();
    }

    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Get node children
     *
     * @return ArrayList<Node>
     */
    public ArrayList<Node> getChildren() {
        return children;
    }

    public Optional<Node> getChildWithSegment(String segment) {
        Node found = null;

        for (Node node : children) {
            if (node.getPattern().toString().equals(segment)) {
                found = node;
                break;
            }
        }

        return Optional.ofNullable(found);
    }

    /**
     * Check if the node can be used as a leaf route
     *
     * @return boolean
     */
    public boolean isLeaf() {
        return handler != null;
    }

    public boolean isRoot() {
        return parent == null;
    }

    /**
     * Get the node parent
     * null is returned for the root node "/"
     *
     * @return null or node parent
     */
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Add a new child node
     *
     * @param newNode The node that should be inserted as a child
     */
    public void addChild(Node newNode) {
        int at = 0;

        PatternType currentType = newNode.getPattern().getType();

        for (at = 0; at < children.size(); at++) {
            PatternType againstType = children.get(at).getPattern().getType();
            if (currentType.compareTo(againstType) < 0) {
                break;
            }
        }

        children.add(at, newNode);
    }

    @Override
    public String toString() {
        return pattern.toString();
    }

    /**
     * Match segment against pattern.
     *
     * @param segment Segment of url that should be matched
     * @return A matcher containing the state and data
     */
    public MatchResult match(String segment) {
        return pattern.match(segment);
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }
}
