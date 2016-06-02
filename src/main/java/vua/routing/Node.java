package vua.routing;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Optional;

public class Node {


    private String segment;
    private Pattern pattern;
    private Object handler;
    private Node parent = null;


    private ArrayList<Node> children = new ArrayList<>();

    public Node(String pattern, Object handler) {
        this.segment = pattern;
        this.pattern = new Pattern(segment);
        this.handler = handler;
    }

    public Node(String pattern) {
        this.segment = pattern;
        this.pattern = new Pattern(segment);
    }

    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Get node children
     *
     * @return ArrayList<Node>
     */
    @NotNull
    public ArrayList<Node> getChildren() {
        return children;
    }

    public Optional<Node> getChildWithSegment(String segment) {
        Node found = null;

        for (Node node : children) {
            if (node.getPattern().equals(segment)) {
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

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Add a new child node
     *
     * @param newNode
     */
    public void addChild(Node newNode) {
        children.add(newNode);
    }

    public String toString() {
        return String.format("[Node pattern=%s]", pattern);
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

    public void setHandler(Object handler) {
        this.handler = handler;
    }
}
