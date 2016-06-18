package vua.http;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;


public class QueryMapTest {

    private String[] stringList(String ...values) {
        return values;
    }

    @Test
    public void testQueryMapParse() {
        HashMap<String, String[]> params = new HashMap<>();
        params.put("user[username]", stringList("ionut"));
        params.put("user[password]", stringList("password"));
        params.put("user[location][st]", stringList("10"));
        params.put("user[location][name]", stringList("test"));
        QueryMap map = new QueryMap(params);

        assertEquals(map.get("user").get("username").value(), "ionut");
        assertEquals(map.get("user").get("password").value(), "password");
    }

    @Test
    public void testQueryMapNotFound() {
        HashMap<String, String[]> params = new HashMap<>();
        params.put("user[username]", stringList("ionut"));
        params.put("user[password]", stringList("password"));
        QueryMap map = new QueryMap(params);

        assertTrue(map.get("user").get("name") instanceof QueryMap.EmptyQueryMap);
    }

    @Test
    public void testQueryMapGet() {
        HashMap<String, String[]> params = new HashMap<>();
        params.put("user", stringList("ionut"));
        params.put("password", stringList("password"));
        QueryMap map = new QueryMap(params);

        assertEquals(map.get("user").value(), "ionut");
    }

    @Test
    public void testQueryMapValueAs() {
        HashMap<String, String[]> params = new HashMap<>();
        params.put("user", stringList("ionut"));
        params.put("age", stringList("10"));
        QueryMap map = new QueryMap(params);

        assertEquals(map.get("age").as(Integer.class), 10);
    }
}