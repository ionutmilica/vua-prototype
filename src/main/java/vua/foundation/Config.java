package vua.foundation;

import org.eclipse.jetty.util.resource.Resource;

import java.util.Properties;

public class Config {

    private Properties properties = new Properties();

    public Config() {
        init();
    }

    private void init() {
        try {
            Resource resource = Resource.newClassPathResource("app.properties");
            properties.load(resource.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, String _default) {
        return properties.getProperty(key, _default);
    }

    public boolean has(String key) {
        return properties.containsKey(key);
    }

    public Properties getProperties() {
        return  properties;
    }
}
