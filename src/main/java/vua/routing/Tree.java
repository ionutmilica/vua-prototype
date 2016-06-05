package vua.routing;

import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class Tree {

    private Node root;

    Tree() {
        root = new Node("/", null);
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
     * Insert node into the tree.
     * The pattern is split into segments, so every segment will be a node into the trie
     *
     * @param pattern Route pattern
     * @param handler The callable object that contains the lambda function or an action
     * @return Leaf node where the handler is stored
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

        if (currentNode.getPattern().getType() == PatternType.PARAM && currentNode.getPattern().isOptional()) {
            if (currentNode.getParent().isLeaf()) {
                String e = String.format("`%s` node already has a handler and can't be combined with an optional segment!", currentNode);
                throw new RuntimeException(e);
            }
            currentNode.getParent().setHandler(handler);
        }

        currentNode.setHandler(handler);

        return currentNode;
    }

    /**
     * Given a full path, the Trie search algorithm is applied
     * so every segment of the path will be matched against a node
     *
     * @param pattern The url path that should be mached
     * @return A NodeMatchResult containing the node, params and the state of the matching
     */
    public NodeMatchResult match(String pattern) {
        String[] segments = pattern.split("/");

        Node currentNode = root;
        NodeMatchResult matchResult = new NodeMatchResult();

        for (String segment : segments) {
            if (segment.isEmpty()) {
                continue;
            }

            Node before = currentNode;

            for (Node child : currentNode.getChildren()) {
                MatchResult childMatchResult = child.match(segment);

                if (childMatchResult.isMatched()) {
                    matchResult.combine(childMatchResult);
                    currentNode = child;
                    break;
                }
            }

            if (before == currentNode) {
                currentNode = null;
                break;
            }
        }

        if (currentNode != null && currentNode.isLeaf()) {
            matchResult.setMatched(true);
            matchResult.setNode(currentNode);
        }

        return matchResult;
    }

    /**
     * For a given node, this method will walk to every child of it and save into the
     * HashMap the relation between them.
     * For example, it can be called with the root node and in the end we'll have all the possible relations
     * so we can generate a dot notation.
     *
     * @param node Starting node
     * @param groups When the relations are stored
     */
    private void walk(Node node, HashMap<String, String> groups) {
        if (node == null) {
            return;
        }

        for (Node child : node.getChildren()) {
            walk(child, groups);
            groups.put(child.toString(), node.toString());
        }
    }

    /**
     * The tree can dump its state at any given time
     *
     * @return dot graph notation that can be viewed nicely with GraphViz
     */
    public String dump() {
        HashMap<String, String> groups = new HashMap<>();

        walk(root, groups);

        String format = "graph Router \n{\n%s\n}";
        StringBuilder links = new StringBuilder();

        for (Map.Entry<String, String> entry : groups.entrySet()) {
            links.append(String.format("\t\"%s\" -- \"%s\";\n", entry.getKey(), entry.getValue()));
        }

        return String.format(format, links.toString());
    }
}
