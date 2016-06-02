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

        assertEquals("/", tree.getRoot().getPattern().toString());
        assertEquals("home", tree.getRoot().getChildren().get(0).getPattern().toString());
        assertEquals("yey", n.getPattern().toString());
    }

    @Test
    public void testMatchNode() {
        Tree tree = new Tree();
        Node n = tree.insert("/home/yey", new Object());

        NodeMatchResult m = tree.match("home/yey");

        assertTrue(m.isMatched());
    }
}