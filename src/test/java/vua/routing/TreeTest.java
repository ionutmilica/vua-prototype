package vua.routing;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
        String[] routes = new String[]{
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

    private NodeMatchResult createMatch(boolean isMatched, String... args) {
        NodeMatchResult result = new NodeMatchResult();
        result.setMatched(isMatched);

        for (int i = 0; i < args.length; i += 2) {
            result.getResult().put(args[i], args[i + 1]);
        }

        return result;
    }

    @Test
    public void testParamRoutes() {
        String[] routes = new String[]{
                "post/hello-world",
                "post/{post}",
                "pages/{page}",
        };

        Tree tree = makeTree(routes);
        System.out.println(tree.getRoot().getChildren());
        System.out.println(tree.dump());

        HashMap<String, MatchResult> toMatch = new HashMap<>();

        toMatch.put("post/hello-world", createMatch(true));
        toMatch.put("post/my-awesome-post", createMatch(true, "post", "my-awesome-post"));
        toMatch.put("posts/some-post", createMatch(false));
        toMatch.put("post/アニメ", createMatch(true, "post", "アニメ"));
        toMatch.put("post/holla/ups", createMatch(false));
        toMatch.put("post/something/10", createMatch(false));

        for (Map.Entry<String, MatchResult> entry : toMatch.entrySet()) {
            MatchResult res = tree.match(entry.getKey());

            assertEquals(res, entry.getValue());
        }
    }

    @Test
    public void testMoreParamRoutes() {
        String[] routes = new String[]{
                "users/{id}",
                "users/profile/{user}",
                "call/{me}/{please}",
        };

        Tree tree = makeTree(routes);

        HashMap<String, MatchResult> toMatch = new HashMap<>();

        toMatch.put("/users/ionut", createMatch(true, "id", "ionut"));
        toMatch.put("/users/10", createMatch(true, "id", "10"));

        // To think about this option
        toMatch.put("/users/profile", createMatch(false));
        toMatch.put("/users/profile/ionut", createMatch(true, "user", "ionut"));
        toMatch.put("/users/profile/10", createMatch(true, "user", "10"));
        toMatch.put("/users/profile/アニメ", createMatch(true, "user", "アニメ"));
        toMatch.put("/call", createMatch(false));
        toMatch.put("/call/me", createMatch(false));
        toMatch.put("/call/me/please/", createMatch(true, "me", "me", "please", "please"));

        for (Map.Entry<String, MatchResult> entry : toMatch.entrySet()) {
            MatchResult res = tree.match(entry.getKey());
            assertEquals(res, entry.getValue());
        }
    }

    @Test
    public void testOptionalParamRoutes() {
        String[] routes = new String[]{
                "users/{id?}",
                "users/profile/{user}",
        };

        Tree tree = makeTree(routes);

        HashMap<String, MatchResult> toMatch = new HashMap<>();

        toMatch.put("users/ionut", createMatch(true, "id", "ionut"));
        toMatch.put("users/10", createMatch(true, "id", "10"));
        toMatch.put("users", createMatch(true));
        toMatch.put("users/profile/ionut", createMatch(true, "user", "ionut"));

        for (Map.Entry<String, MatchResult> entry : toMatch.entrySet()) {
            MatchResult res = tree.match(entry.getKey());
            assertEquals(res, entry.getValue());
        }
    }

    @Test
    public void testRegexParamRoutes() {
        String[] routes = new String[]{
                "post/{title}-{id}",
                "{something}-us",
        };

        Tree tree = makeTree(routes);

        HashMap<String, MatchResult> toMatch = new HashMap<>();

        toMatch.put("post/hello-summer-10", createMatch(true, "title", "hello-summer", "id", "10"));
        toMatch.put("about-us", createMatch(true, "something", "about"));

        for (Map.Entry<String, MatchResult> entry : toMatch.entrySet()) {
            MatchResult res = tree.match(entry.getKey());
            assertEquals(res, entry.getValue());
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