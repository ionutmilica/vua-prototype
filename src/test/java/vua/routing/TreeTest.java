package vua.routing;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeTest {

    // Helpers
    private Tree makeTree(String[] routes) {
        Tree tree = new Tree();
        for (String route : routes) {
            tree.insert(route, new Object());
        }
        return tree;
    }

    @Test
    public void testTreeCreation() {
        Tree tree = new Tree();

        assertNotEquals(tree.getRoot(), null);
    }

    @Test
    public void testStaticRoutes() {
        String[] routes = new String[] {
            "post/hello-world",
            "post/another-post",
            "post/more-post",
            "pages/about",
            "pages/contact-us",
            "/",
            "about-us",
            "files/index.html",
        };

        Tree tree = makeTree(routes);

        for (String route : routes) {
            NodeMatchResult match = tree.match(route);
            assertTrue(match.isMatched());
        }
    }

    @Test
    public void testInsertPattern() {
        Tree tree = new Tree();
        Node n = tree.insert("/home/yey", null);

        assertEquals("/", tree.getRoot().toString());
        assertEquals("home", tree.getRoot().getChildren().get(0).toString());
        assertEquals("yey", n.toString());
    }

    @Test
    public void testMatchNode() {
        Tree tree = new Tree();
        Node n = tree.insert("/home/yey", new Object());

        NodeMatchResult m = tree.match("home/yey");

        assertTrue(m.isMatched());
    }
}