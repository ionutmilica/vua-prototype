package vua.http;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryMap {

    private static QueryMap NULL = new EmptyQueryMap();

    private Map<String, QueryMap> queryMap = new HashMap<>();

    private String[] values;

    QueryMap(Map<String, String[]> params) {
        initMap(params);
    }

    protected QueryMap() {
    }

    /**
     * Get query values
     *
     * @param keys
     * @return
     */
    public QueryMap get(String... keys) {
        QueryMap current = this;
        for (String key : keys) {
            if (current.queryMap.containsKey(key)) {
                current = current.queryMap.get(key);
            } else {
                current = NULL;
            }
        }
        return current;
    }

    /**
     * Get the first value
     *
     * @return String containing the value of the query
     */
    public String value() {
        return hasValue() ? values[0] : null;
    }

    public String[] values() {
        return values.clone();
    }

    public boolean hasValue() {
        return values != null && values.length > 0;
    }

    public boolean hasArrayValue() {
        return values != null && values.length > 1;
    }

    Map<String, QueryMap> getQueryMap() {
        return queryMap;
    }

    public String toString() {
        return value();
    }

    /**
     * Get query value as type
     *
     * @param to
     * @return
     */
    public Object as(Class<?> to) {
        return ConvertUtils.convert(value(), to);
    }

    private void initMap(Map<String, String[]> params) {
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String[] subKeys = getCleanKeys(entry.getKey());

            QueryMap current = this;
            for (String str : subKeys) {
                QueryMap tmp = null;
                if (!current.queryMap.containsKey(str)) {
                    tmp = new QueryMap();
                    current.queryMap.put(str, tmp);
                } else {
                    tmp = current.queryMap.get(str);
                }

                current = tmp;
            }
            current.values = entry.getValue();
        }
    }

    static class EmptyQueryMap extends QueryMap {
    }

    /**
     * Get clean query keys
     * <p>
     * user[password]  ->  user, password
     * user[location][st] -> user, location, st
     *
     * @param key
     * @return
     */
    private String[] getCleanKeys(String key) {
        ArrayList<String> subKeys = new ArrayList<>();

        String clean = key.replace("]", "").replace("[", " ");
        return clean.split(" ");
    }
}
