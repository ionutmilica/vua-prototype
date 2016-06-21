package vua.http;

import javax.servlet.http.HttpSession;
import java.util.*;

public class Session {

    private final HttpSession session;
    private final Request request;

    public Session(HttpSession session, Request request) {
        this.session = session;
        this.request = request;
    }

    /**
     * Session id
     *
     * @return Id of the session
     */
    public String id() {
        return session.getId();
    }

    /**
     * The last time when the client sent a request with this session
     * @return Unix timestamp
     */
    public long lastAccessedTime() {
        return session.getLastAccessedTime();
    }

    /**
     * Get the max inactive interval before a session is destroyed
     *
     * @return Seconds
     */
    public int maxInactiveInterval() {
        return session.getMaxInactiveInterval();
    }

    /**
     * Set the max inactive interval before a session is destroyed
     *
     * @param interval seconds
     */
    public void maxInactiveInterval(int interval) {
        session.setMaxInactiveInterval(interval);
    }

    /**
     * Returns <code>true</code> if the client does not yet know about the
     * session or if the client chooses not to join the session.  For
     * example, if the server used only cookie-based sessions, and
     * the client had disabled the use of cookies, then a session would
     * be new on each request.
     */
    public boolean isNew() {
        return session.isNew();
    }

    /**
     * Check if session has key
     *
     * @param name Key that is used to search for the object
     * @return true if there is a key in the session, otherwise false
     */
    public boolean has(String name) {
        return get(name) != null;
    }

    /**
     * Get session object by name
     *
     * @param name name of the object stored in the session
     * @return Object that contains the stored object
     */
    public Object get(String name) {
        return session.getAttribute(name);
    }

    public Object get(String name, Object _default) {
        Object val = get(name);
        return val == null ? _default : val;
    }

    /**
     * Retrieve and delete an item from the session
     *
     * @param name name of the item to be retrieved
     * @return Object that needs to be retrieved
     */
    public Object pull(String name) {
        Object value = get(name);
        forget(name);
        return value;
    }
    /**
     * Retrieve and delete an item from the session
     *
     * @param name name of the item to be retrieved
     * @param _default provide a default value if the object is not found
     * @return Object that needs to be retrieved
     */
    public Object pull(String name, Object _default) {
        Object value = pull(name);
        return value == null ? _default : value;
    }

    /**
     * Store a new key-value into the session
     *
     * @param key Key used to identify an resource saved in the sessions
     * @param value
     */
    public void put(String key, Object value) {
        session.setAttribute(key, value);
    }

    /**
     * Delete a element from the session
     *
     * @param name
     */
    public void forget(String name) {
        session.removeAttribute(name);
    }

    /**
     * Remove all data from the session
     *
     */
    public void clear() {
        request.setValidSession(false);
        session.invalidate();
    }

    /**
     * Get all the session objects in a hash map
     *
     * @return
     */
    public Map<String, Object> all() {
        HashMap<String, Object> objects = new HashMap<>();

        Enumeration<String> enumeration = session.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            objects.put(key, get(key));
        }

        return objects;
    }

    /**
     * Get session keys
     *
     * @return an <code>Enumeration</code> of <code>String</code> objects
     * containing the names of all the objects bound to this session.
     */
    public Set<String> keys() {
        TreeSet<String> attributes = new TreeSet<>();
        Enumeration<String> enumeration = session.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            attributes.add(enumeration.nextElement());
        }
        return attributes;
    }

    /**
     * Get raw session object
     *
     * @return HttpSession
     */
    public HttpSession raw() {
        return session;
    }
}
