package vua.routing;

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
    public Node insert(String pattern, Object handler) {
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
        } else {
            matchResult.reset();
        }

        return matchResult;
    }

    private int start = 1;

    /**
     * For a given node, this method will walk to every child of it and save into StringBuilder relation between them.
     * For example, it can be called with the root node and in the end we'll have all the possible relations
     * so we can generate a dot notation.
     *
     * @param node Starting node
     * @param nodes Builder where we store every unique node
     * @param relationships Where every relation between two nodes are stored
     */
    private void walk(Node node, StringBuilder nodes, StringBuilder relationships) {
        if (node == null) {
            return;
        }

        int level = start;

        for (Node child : node.getChildren()) {
            start++;
            nodes.append(String.format("\t%d [label=\"%s\"] ", start, child.getPattern().getCompiled()));
            if (child.isLeaf()) {
                nodes.append("[peripheries = 2] ");
            }
            nodes.append(";\n");
            relationships.append(String.format("\t%d -- %d;\n", level, start));

            walk(child, nodes, relationships);
        }
    }

    /**
     * The tree can dump its state at any given time
     *
     * @return dot graph notation that can be viewed nicely with GraphViz
     */
    public String dump() {
        start = 1;

        StringBuilder nodes = new StringBuilder();
        StringBuilder relationships = new StringBuilder();

        // Add the root node
        nodes.append(String.format("\t%d [label=\"%s\"] ", start, root.getPattern().getCompiled()));
        if (root.isLeaf()) {
            nodes.append("[peripheries = 2] ");
        }
        nodes.append(";\n");

        walk(root, nodes, relationships);

        String format = "graph Router \n{\n%s%s\n}";

        return String.format(format, nodes.toString(), relationships.toString());
    }
}
