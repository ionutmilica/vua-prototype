package vua.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageBag {

    private LinkedHashMap<String, ArrayList<String>> messages;

    private String format = ":message";

    public MessageBag() {
        messages = new LinkedHashMap<>();
    }

    public void add(String key, String value) {
        ArrayList<String> list;

        if (messages.containsKey(key)) {
            list = messages.get(key);
        } else {
            list = new ArrayList<>();
        }

        // Add unique messages
        if (!list.contains(value)) {
            list.add(value);
        }

        messages.put(key, list);
    }

    /**
     * Get messages for a given key
     *
     * @param key
     * @return ArrayList of String messages if the key exists or empty list if it does not
     */
    public ArrayList<String> get(String key) {
        ArrayList<String> tmp = format(messages.get(key), format, key);
        if (tmp == null) {
            return new ArrayList<>();
        }
        return tmp;
    }

    /**
     * Check if message bag contains a given key
     *
     * @param key
     * @return boolean
     */
    public boolean has(String key) {
        return messages.containsKey(key);
    }

    /**
     * Get all messages
     *
     * @return list of messages
     */
    public ArrayList<String> first() {
        Map.Entry<String, ArrayList<String>> entry = messages.entrySet().iterator().next();
        return entry.getValue();
    }

    /**
     * Get the first message by a key
     *
     * @param key
     * @return
     */
    public String first(String key) {
        ArrayList<String> values = format(messages.get(key), format, key);

        if (values == null || values.size() == 0) {
            return null;
        }

        return values.get(0);
    }


    /**
     * Flatten all the message categories into an array list
     *
     * @return
     */
    public ArrayList<String> all() {
        ArrayList<String> all = new ArrayList<>();

        for (Map.Entry<String, ArrayList<String>> entry : messages.entrySet()) {
            all.addAll(format(entry.getValue(), format, entry.getKey()));
        }

        return all;
    }

    /**
     * Check if message bag is empty
     *
     * @return true if there are no messages otherwise false
     */
    public boolean isEmpty() {
        return messages.size() == 0;
    }

    /**
     * Get the default message format.
     *
     * @return string
     */
    public String getFormat() {
        return format;
    }

    /**
     * Set the default message format.
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Get raw messages
     *
     * @return Map of messages
     */
    public LinkedHashMap<String, ArrayList<String>> getMessages() {
        return messages;
    }

    private ArrayList<String> format(ArrayList<String> messages, String format, String key) {
        return messages.stream()
                .map(message -> formatLine(format, key, message))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String formatLine(String format, String key, String message) {
        if (format.equals(":message")) {
            return message;
        }
        if (format.equals(":key")) {
            return key;
        }
        return format.replace(":key", key).replace(":message", message);
    }

    @Override
    public String toString() {
        return messages.toString();
    }
}
