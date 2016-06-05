package vua.routing;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void testNodeCreation() {
        Node n = new Node("/");
        assertEquals(n.getPattern().toString(), "/");
    }

    @Test
    public void testNodeMatching() {
        Node n = new Node("house");
        MatchResult m = n.match("house");
        MatchResult nm = n.match("cloud");

        assertTrue(m.isMatched());
        assertFalse(nm.isMatched());
    }

    @Test
    public void testChildOrder() {
        ArrayList<Node> expected = new ArrayList<>();
        expected.add(new Node("terms"));
        expected.add(new Node("static-page"));
        expected.add(new Node("{user}-{password}"));
        expected.add(new Node("{page}"));

        Node root = new Node("/");
        root.addChild(expected.get(2));
        root.addChild(expected.get(0));
        root.addChild(expected.get(3));
        root.addChild(expected.get(1));

        assertTrue(expected.equals(root.getChildren()));
        assertEquals(root.getChildren().size(), 4);
    }
}