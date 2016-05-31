package vua.routing;

import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {

    @Test
    public void testNodeCreation() {
        Node n = new Node("/");
        assertEquals(n.getPattern(), "/");
    }

    @Test
    public void testNodeMatching() {
        Node n = new Node("house");
        Matcher m = n.match("house");
        Matcher nm = n.match("cloud");

        assertTrue(m.isMatched());
        assertFalse(nm.isMatched());
    }
}