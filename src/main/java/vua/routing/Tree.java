package vua.routing;

import com.sun.istack.internal.NotNull;

import java.util.Optional;

class Tree {

    private Node root = new Node("/", null);

    Tree() {

    }

    /**
     * Get tree root node. By convention is '/' with no callable
     *
     * @return Node
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Insert node into the tree
     *
     * @param pattern
     * @param handler
     * @return
     */
    public Node insert(String pattern, @NotNull Object handler) {
        String[] segments = pattern.split("/");

        Node currentNode = root;

        for (String segment : segments) {
            if (segment.isEmpty()) {
                continue;
            }

            Optional<Node> node = currentNode.getChildWithSegment(segment);

            if (node.isPresent()) {
                currentNode = node.get();
            } else {
                Node newNode = new Node(segment);
                newNode.setParent(currentNode);
                currentNode.addChild(newNode);
                currentNode = newNode;
            }

        }
        currentNode.setHandler(handler);

        return currentNode;
    }

    public Matcher match(String pattern) {
        String[] segments = pattern.split("/");

        Node currentNode = root;
        Matcher matcher = new Matcher();

        for (String segment : segments) {
            if (segment.isEmpty()) {
                continue;
            }

            Node before = currentNode;

            for (Node child : currentNode.getChildren()) {
                Matcher childMatcher = child.match(segment);

                if (childMatcher.isMatched()) {
                    matcher.combine(childMatcher);
                    currentNode = child;
                    break;
                }
            }

            if (before == currentNode) {
                currentNode = null;
            }
        }

        if (currentNode != null && currentNode.isLeaf()) {
            matcher.setMatched(true);
            matcher.setNode(currentNode);
        }

        return matcher;
    }
}
