package vua.routing;

import org.junit.Test;

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
}