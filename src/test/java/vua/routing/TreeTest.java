package vua.routing;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeTest {

    @Test
    public void testTreeCreation() {
        Tree tree = new Tree();

        assertNotEquals(tree.getRoot(), null);
    }

    @Test
    public void testInsertPattern() {
        Tree tree = new Tree();
        Node n = tree.insert("/home/yey", null);

        assertEquals("/", tree.getRoot().getPattern());
        assertEquals("home", tree.getRoot().getChildren().get(0).getPattern());
        assertEquals("yey", n.getPattern());
    }

    @Test
    public void testMatchNode() {
        Tree tree = new Tree();
        Node n = tree.insert("/home/yey", new Object());

        Matcher m = tree.match("home/yey");

        assertTrue(m.isMatched());
    }
}