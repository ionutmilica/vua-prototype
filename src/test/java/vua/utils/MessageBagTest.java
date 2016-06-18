package vua.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MessageBagTest {

    private ArrayList<String> newList(String ...items) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, items);
        return list;
    }

    @Test
    public void testNonDuplicates() {
        MessageBag bag = new MessageBag();
        bag.add("username", "failed");
        bag.add("username", "failed");

        HashMap<String, ArrayList<String>> messages = bag.getMessages();

        assertEquals(1, messages.size());
        assertEquals(1, messages.get("username").size());
    }

    @Test
    public void testMessagesAreAdded() {
        MessageBag bag = new MessageBag();
        bag.add("names", "ion");
        bag.add("names", "ana");
        bag.add("numbers", "11");

        HashMap<String, ArrayList<String>> messages = bag.getMessages();

        assertEquals(newList("ion", "ana"), messages.get("names"));
        assertEquals(newList("11"), messages.get("numbers"));
    }

    @Test
    public void testGetMessagesByKey() {
        MessageBag bag = new MessageBag();
        bag.add("numbers", "11");
        bag.add("numbers", "13");
        bag.add("numbers", "1");

        assertEquals(newList("11", "13", "1"), bag.get("numbers"));
    }

    @Test
    public void checkReturnFirstMessage() {
        MessageBag bag = new MessageBag();
        bag.add("numbers", "11");
        bag.add("numbers", "13");
        bag.add("numbers", "1");
        bag.add("names", "John");

        assertEquals("11", bag.first("numbers"));
        assertEquals("John", bag.first("names"));
    }

    @Test
    public void testIfBagHasMessages() {
        MessageBag bag = new MessageBag();
        bag.add("numbers", "11");

        assertTrue(bag.has("numbers"));
        assertFalse(bag.has("digits"));
    }

    @Test
    public void testFormat() {
        MessageBag bag = new MessageBag();
        bag.setFormat("<b>:message</b>");
        bag.add("names", "Johnny");
        bag.add("names", "Joe");
        bag.add("numbers", "10");

        assertEquals("<b>Johnny</b>", bag.first("names"));
        assertEquals(newList("<b>Johnny</b>", "<b>Joe</b>"), bag.get("names"));
        assertEquals(newList("<b>Johnny</b>", "<b>Joe</b>", "<b>10</b>"), bag.all());

        /*
        assertEquals("Johnny", bag.first("names", ":message"));
        assertEquals(newList("Johnny", "Joe"), bag.get("names"));
        assertEquals(newList("Johnny", "Joe", "10"), bag.all(":message")); */
    }
}